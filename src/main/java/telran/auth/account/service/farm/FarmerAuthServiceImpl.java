package telran.auth.account.service.farm;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.auth.account.dao.FarmerRepository;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.model.Farmer;
import telran.auth.security.jwt.RevokedTokenService;
import telran.auth.security.jwt.farmer.FarmJwtServiceImpl;
import telran.exceptions.InvalidUserDataException;
import telran.exceptions.UserAlreadyExistsException;
import telran.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerAuthServiceImpl implements FarmAuthService {
	private final FarmerRepository farmerRepository;
	private final FarmJwtServiceImpl jwtService;
	private final RevokedTokenService revokedTokenService;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public AuthResponse registerFarmer(FarmerDto farmerDto) {
		if (farmerDto.getEmail() == null || farmerDto.getPassword() == null) {
			throw new InvalidUserDataException("Email and password cannot be null");
		}

		if (farmerRepository.findByEmail(farmerDto.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Farmer with this email already exists!");
		}

		Farmer farmer = new Farmer(farmerDto.getEmail(), passwordEncoder.encode(farmerDto.getPassword()),
				farmerDto.getFarmName(), farmerDto.getLanguage() != null ? farmerDto.getLanguage() : "en",
				farmerDto.getTimezone() != null ? farmerDto.getTimezone() : "Europe/Berlin", farmerDto.getLocation());
		farmer.setRegisteredAt(ZonedDateTime.now());
		farmer.setLastLoginAt(ZonedDateTime.now());
		farmerRepository.save(farmer);

		String accessToken = jwtService.generateAccessToken(farmer.getEmail(), "FARMER");
		String refreshToken = jwtService.generateRefreshToken(farmer.getEmail());

		return new AuthResponse(farmer.getId(), farmer.getEmail(), accessToken, refreshToken, "FARMER");
	}

	@Override
	public AuthResponse authenticateFarmer(AuthRequestDto request) {
		Farmer farmer = farmerRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidUserDataException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), farmer.getPassword())) {
			throw new InvalidUserDataException("Invalid email or password");
		}

		farmer.setLastLoginAt(ZonedDateTime.now());
		farmerRepository.save(farmer);

		String accessToken = jwtService.generateAccessToken(farmer.getEmail(), "FARMER");
		String refreshToken = jwtService.generateRefreshToken(farmer.getEmail());

		return new AuthResponse(farmer.getId(), farmer.getEmail(), accessToken, refreshToken, "FARMER");
	}

	@Override
	public void logout(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
			revokedTokenService.revokeToken(token);
		}
	}

	@Override
	public FarmerDto getFarmer(String email) {
		Farmer farmer = farmerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Farmer not found: " + email));
		return new FarmerDto(farmer.getId(), farmer.getEmail(), "********", farmer.getFarmName(), farmer.getLanguage(),
				farmer.getTimezone(), farmer.getLocation());
	}

	@Override
	public AuthResponse refreshAccessToken(String refreshToken) {
		log.info("Refreshing access token for refreshToken: {}", refreshToken);

		if (!jwtService.validateToken(refreshToken)) {
			log.warn("Invalid or expired refresh token: {}", refreshToken);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
		}
		String email = jwtService.extractEmail(refreshToken);
		String role = "FARMER";
		String newAccessToken = jwtService.generateAccessToken(email, role);

		return new AuthResponse(null, email, newAccessToken, refreshToken, role);

	}

}
