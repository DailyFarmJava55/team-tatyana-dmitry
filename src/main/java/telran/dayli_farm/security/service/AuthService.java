package telran.dayli_farm.security.service;

import static telran.dayli_farm.api.message.ErrorMessages.INVALID_TOKEN;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.entity.CustomerCredential;
import telran.dayli_farm.farmer.dao.FarmerCredentialRepository;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;
import telran.dayli_farm.security.CustomUserDetails;
import telran.dayli_farm.security.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthService {
	private final CustomerRepository customerRepo;
	private final CustomerCredentialRepository customerCredentialRepo;
	private final FarmerRepository farmerRepo;
	private final FarmerCredentialRepository farmerCredentialRepo;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public TokenResponseDto authenticate(String email, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		UUID userId = userDetails.getId();
		LocalDateTime now = LocalDateTime.now();

		Optional<CustomerCredential> customerOpt = customerCredentialRepo.findByCustomerEmail(email);
		if (customerOpt.isPresent()) {
			CustomerCredential customerCredential = customerOpt.get();
			String refreshToken = jwtService.generateRefreshToken(userId, email);
			String accessToken = jwtService.generateAccessToken(userId, email, "CUSTOMER");

			customerCredential.setRefreshToken(refreshToken);
			customerCredential.setLastLogin(now);
			customerCredentialRepo.save(customerCredential);

			return new TokenResponseDto(accessToken, refreshToken);
		}

		Optional<FarmerCredential> farmerOpt = farmerCredentialRepo.findByFarmerEmail(email);
		if (farmerOpt.isPresent()) {
			FarmerCredential farmerCredential = farmerOpt.get();
			String refreshToken = jwtService.generateRefreshToken(userId, email);
			String accessToken = jwtService.generateAccessToken(userId, email, "FARMER");

			farmerCredential.setRefreshToken(refreshToken);
			farmerCredential.setLastLogin(now);
			farmerCredentialRepo.save(farmerCredential);

			return new TokenResponseDto(accessToken, refreshToken);
		}

		throw new UsernameNotFoundException("User not found with email: " + email);
	}

	public ResponseEntity<RefreshTokenResponseDto> refreshCustomerAccessToken(String refreshToken) {
		log.info("AuthService.refreshCustomerAccessToken - Refreshing access token for Customer: " + refreshToken);

		UUID id = jwtService.extractUserId(refreshToken);
		Optional<Customer> customerOptional = customerRepo.findById(id);
		CustomerCredential customerCredential = customerCredentialRepo.findByCustomer(new Customer(id));

		if (customerOptional.isPresent() && customerCredential.getRefreshToken().equals(refreshToken)
				&& !jwtService.isTokenExpired(refreshToken)) {

			String newAccessToken = jwtService.generateAccessToken(id, customerOptional.get().getEmail(), "CUSTOMER");
			return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
		}

		throw new BadCredentialsException(INVALID_TOKEN);
	}

	public ResponseEntity<RefreshTokenResponseDto> refreshFarmerAccessToken(String refreshToken) {
		log.info("AuthService.refreshFarmerAccessToken - Refreshing access token for Farmer: " + refreshToken);

		UUID id = jwtService.extractUserId(refreshToken);
		Optional<Farmer> farmerOptional = farmerRepo.findByid(id);
		FarmerCredential farmerCredential = farmerCredentialRepo.findByFarmer(new Farmer(id));

		if (farmerOptional.isPresent() && farmerCredential.getRefreshToken().equals(refreshToken)
				&& !jwtService.isTokenExpired(refreshToken)) {

			String newAccessToken = jwtService.generateAccessToken(id, farmerOptional.get().getEmail(), "FARMER");
			return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
		}

		throw new BadCredentialsException(INVALID_TOKEN);
	}
}
