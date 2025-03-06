package telran.auth.account.service.farm;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.AuthResponse;
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
	public AuthResponse registerFarmer(FarmerDto farmerDto) {
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
		User newFarmer = userRepository.save(user);

		 Authentication auth = new UsernamePasswordAuthenticationToken(
	                newFarmer.getEmail(),
	                newFarmer.getPassword(),
	                newFarmer.getRoles().stream()
	                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
	                        .toList()
	        );
		 String token = jwtService.generateToken(auth);
		return  new AuthResponse(newFarmer.getId(), newFarmer.getEmail(), newFarmer.getRoles(), token);
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

	@Override
	public void updateLastLogin(UUID id) {
		User farmer = getFarmerById(id);
		farmer.setLastLoginAt(ZonedDateTime.now());
		userRepository.save(farmer);
		
	}

	private User getFarmerById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Farmer not found"));
	}

}
