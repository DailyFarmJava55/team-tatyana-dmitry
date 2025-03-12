package telran.auth.account.service.farm;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.FarmerRepository;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.Farmer;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class FarmerAuthServiceImpl implements FarmAuthService {
	private final FarmerRepository farmerRepository;
	private final JwtService jwtService;
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

		Farmer newFarmer = farmerRepository.save(farmer);

		String accessToken = jwtService.generateAccessToken(newFarmer.getEmail(), "FARMER");
		String refreshToken = jwtService.generateRefreshToken(newFarmer.getEmail());

		return new AuthResponse(newFarmer.getId(),newFarmer.getEmail(), accessToken, refreshToken);
	}

	@Override
	public AuthResponse authenticateFarmer(AuthRequestDto request) {
		Farmer farmer = farmerRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidUserDataException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), farmer.getPassword())) {
			throw new InvalidUserDataException("Invalid email or password");
		}

		String accessToken = jwtService.generateAccessToken(farmer.getEmail(), "FARMER");
		String refreshToken = jwtService.generateRefreshToken(farmer.getEmail());

		return new AuthResponse(farmer.getId(),farmer.getEmail(), accessToken, refreshToken);
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
	public void updateLastLogin(UUID id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Farmer not found with id: " + id));
		farmer.setLastLoginAt(ZonedDateTime.now());
		farmerRepository.save(farmer);

	}

	@Override
	public AuthResponse refreshAccessToken(String refreshToken) {
		if (!jwtService.validateToken(refreshToken)) {
	        throw new RuntimeException("Invalid or expired refresh token");
	    }

	    String email = jwtService.extractEmail(refreshToken);
	    String role = jwtService.extractRole(refreshToken); 
	    String newAccessToken = jwtService.generateAccessToken(email, role);

	    return new AuthResponse(null,email, newAccessToken, refreshToken);
	}

}
