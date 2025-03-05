package telran.auth.account.service.farm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class FarmerAuthServiceImpl implements FarmAuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final RevokedTokenService revokedTokenService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String registerFarmer(FarmerDto farmerDto) {
		if (farmerDto.getEmail() == null || farmerDto.getPassword() == null) {
			throw new InvalidUserDataException("Email and password cannot be null");
		}

		User user = userRepository.findByEmail(farmerDto.getEmail()).orElse(null);

		if (user != null) {
			if (user.getRoles().contains(Role.FARMER)) {
				throw new UserAlreadyExistsException("Farmer with this email already exists!");
			}
			user.getRoles().add(Role.FARMER);
			user.setFarmName(farmerDto.getFarmName());
			user.setLocation(farmerDto.getLocation());
		} else {
			user = new User(farmerDto.getEmail(), passwordEncoder.encode(farmerDto.getPassword()),
					farmerDto.getLanguage() != null ? farmerDto.getLanguage() : "en", farmerDto.getLocation());
			user.getRoles().add(Role.FARMER);
			user.setFarmName(farmerDto.getFarmName());
		}

		user.setTimezone(farmerDto.getTimezone() != null ? farmerDto.getTimezone() : "Europe/Berlin");
		userRepository.save(user);

		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getRoles());

		return jwtService.generateToken(auth);
	}

	public String login(Authentication auth) {
		return jwtService.generateToken(auth);
	}

	@Override
	public void logout(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
			revokedTokenService.revokeToken(token);
		}
	}

	public User findFarmerByEmail(String email) {
		return userRepository.findByEmail(email).filter(user -> user.getRoles().contains(Role.FARMER))
				.orElseThrow(() -> new UsernameNotFoundException("Farmer not found"));
	}

	@Override
	public FarmerDto getFarmer(String email) {
		User farmer = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Farmer not found: " + email));

		if (!farmer.getRoles().contains(Role.FARMER)) {
			throw new UserNotFoundException("User is not a farmer: " + email);
		}

		return new FarmerDto(farmer.getId(), farmer.getEmail(), "********", farmer.getFarmName(), farmer.getLanguage(),
				farmer.getTimezone(), farmer.getLocation());
	}

}
