package telran.auth.account.service.user;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;
import telran.exceptions.InvalidUserDataException;
import telran.exceptions.UserAlreadyExistsException;
import telran.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final RevokedTokenService revokedTokenService;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public AuthResponse registerUser(UserDto userDto) {
		if (userDto.getEmail() == null || userDto.getPassword() == null) {
			throw new InvalidUserDataException("Email and password cannot be null");
		}

		if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("User with this email already exists!");
		}

		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
				userDto.getLanguage() != null ? userDto.getLanguage() : "en", userDto.getLocation());
		user.setTimezone(userDto.getTimezone() != null ? userDto.getTimezone() : "Europe/Berlin");
		user.setRegisteredAt(ZonedDateTime.now());
		User newUser = userRepository.save(user);

		String accessToken = jwtService.generateAccessToken(newUser.getEmail(), "USER");
		String refreshToken = jwtService.generateRefreshTokenUser(newUser.getEmail());

		return new AuthResponse(newUser.getId(), newUser.getEmail(), accessToken, refreshToken);
	}

	@Override
	public AuthResponse authenticateUser(AuthRequestDto request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidUserDataException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidUserDataException("Invalid email or password");
		}

		String accessToken = jwtService.generateAccessToken(user.getEmail(), "USER");
		String refreshToken = jwtService.generateRefreshTokenUser(user.getEmail());
		updateLastLogin(user.getId());

		return new AuthResponse(user.getId(), user.getEmail(), accessToken, refreshToken);

	}

	@Transactional
	@Override
	public void updateLastLogin(UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
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
	
	@Override
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

	@Override
	public AuthResponse refreshAccessToken(String refreshToken) {
	    log.info("Refreshing access token for refreshToken: {}", refreshToken);
	    
	    if (!jwtService.validateToken(refreshToken)) {
	        log.warn("Invalid or expired refresh token: {}", refreshToken);
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
	    }

	    String email = Optional.ofNullable(jwtService.extractEmail(refreshToken))
	            .orElseThrow(() -> {
	                log.warn("Invalid token data: email not found in {}", refreshToken);
	                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token data: email not found");
	            });

	    String role = Optional.ofNullable(jwtService.extractRole(refreshToken))
	            .orElseThrow(() -> {
	                log.warn("Invalid token data: role not found in {}", refreshToken);
	                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token data: role not found");
	            });

	    String newAccessToken = jwtService.generateAccessToken(email, role);//"USER");
	    log.info("Generated new access token for email {}", email);

	    return new AuthResponse(null, email, newAccessToken, refreshToken);
	}

}