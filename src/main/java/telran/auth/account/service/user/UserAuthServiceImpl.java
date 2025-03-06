package telran.auth.account.service.user;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final RevokedTokenService revokedTokenService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public AuthResponse registerUser(UserDto userDto) {
		if (userDto.getEmail() == null || userDto.getPassword() == null) {
			throw new InvalidUserDataException("Email and password cannot be null");
		}

		if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("User with this email already exists!");
		}

		User newUser = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
				userDto.getLanguage() != null ? userDto.getLanguage() : "en", userDto.getLocation());

		newUser.getRoles().add(Role.USER);
		newUser.setTimezone(userDto.getTimezone() != null ? userDto.getTimezone() : "Europe/Berlin");

		userRepository.save(newUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword(),
				newUser.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList());
		String token = jwtService.generateToken(auth);
		return new AuthResponse(newUser.getId(), newUser.getEmail(), newUser.getRoles(), token);
	}

	@Override
	public String login(Authentication auth) {
		User user = findUserByEmail(auth.getName());

		if (!passwordEncoder.matches(auth.getCredentials().toString(), user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		updateLastLogin(user.getId());
		return jwtService.generateToken(auth);
	}

	@Override
	public void updateLastLogin(UUID id) {
		User user = getUserById(id);
		user.setLastLoginAt(ZonedDateTime.now());
		userRepository.save(user);

	}

	@Override
	public void logout(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
			revokedTokenService.revokeToken(token);
		}
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Override
	public UserDto getUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found: " + email));

		return new UserDto(user.getId(), user.getEmail(), "********", user.getLanguage(), user.getTimezone(),
				user.getLocation());
	}

	private User getUserById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

}