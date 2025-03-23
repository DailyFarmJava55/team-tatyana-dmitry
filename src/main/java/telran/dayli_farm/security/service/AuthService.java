package telran.dayli_farm.security.service;
import static telran.dayli_farm.api.message.ErrorMessages.INVALID_TOKEN;
import static telran.dayli_farm.api.message.ErrorMessages.WRONG_USER_NAME_OR_PASSWORD;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.entity.Customer;
import telran.dayli_farm.entity.CustomerCredential;
import telran.dayli_farm.security.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	private final CustomerRepository customerRepo;
	private final CustomerCredentialRepository customerCredentialRepo;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public TokenResponseDto authenticate(String email, String password) {
		
		Optional<Customer> customerOpt = customerRepo.findByEmail(email);
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			CustomerCredential customerCredential = customerCredentialRepo.findByCustomer(customer);
			
			log.info("Authenticate. Customer " + customer.getEmail() + " exists");
			log.info("Authenticate. passwordEncoder.matches"
					+ passwordEncoder.matches(password, customerCredential.getHashedPassword()));
			
			if(passwordEncoder.matches(password, customerCredential.getHashedPassword())) {
				log.info("Authenticate. Password is valid");
				String uuid = customer.getId().toString();
				String accessToken = jwtService.generateAccessToken(uuid, email);
				String refreshToken = jwtService.generateRefreshToken(uuid, email);
				
				customerCredential.setRefreshToken(refreshToken);
				customerCredentialRepo.save(customerCredential);
				
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
				return new TokenResponseDto(accessToken, refreshToken);
			}
		}
		throw new BadCredentialsException(WRONG_USER_NAME_OR_PASSWORD);
	}
	

	public ResponseEntity<RefreshTokenResponseDto> refreshCustomerAccessToken(String refreshToken) {
	    log.info("AuthService.refreshCustomerAccessToken - Refreshing access token for Customer: " + refreshToken);

	    UUID id = jwtService.extractUserId(refreshToken);
	    Optional<Customer> customerOptional = customerRepo.findById(id);
	    CustomerCredential customerCredential = customerCredentialRepo.findByCustomer(new Customer(id));
	    
	    log.info("Customer exists: " + customerOptional.isPresent());
	    log.info("Token matches: " + customerCredential.getRefreshToken().equals(refreshToken));
	    log.info("Token expired: " + jwtService.isTokenExpired(refreshToken));
	    
	    if (customerOptional.isPresent() && 
	        !customerCredential.getRefreshToken().isBlank() &&
	        customerCredential.getRefreshToken().equals(refreshToken) && 
	        !jwtService.isTokenExpired(refreshToken)) {

	        String newAccessToken = jwtService.generateAccessToken(id.toString(), customerOptional.get().getEmail());
	        return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
	    }
	    
	    throw new BadCredentialsException(INVALID_TOKEN);
	}

	// Refresh Token для Farmer
//	public ResponseEntity<RefreshTokenResponseDto> refreshFarmerAccessToken(String refreshToken) {
//	    log.info("AuthService.refreshFarmerAccessToken - Refreshing access token for Farmer: " + refreshToken);
//
//	    UUID id = UUID.fromString(jwtService.extractUserId(refreshToken));
//	    Optional<Farmer> farmerOptional = farmerRepo.findByid(id);
//	    FarmerCredential farmerCredential = credentialRepo.findByFarmer(new Farmer(id));
//	    
//	    log.info("Farmer exists: " + farmerOptional.isPresent());
//	    log.info("Token matches: " + farmerCredential.getRefreshToken().equals(refreshToken));
//	    log.info("Token expired: " + jwtService.isTokenExpired(refreshToken));
//	    
//	    if (farmerOptional.isPresent() && 
//	        !farmerCredential.getRefreshToken().isBlank() &&
//	        farmerCredential.getRefreshToken().equals(refreshToken) && 
//	        !jwtService.isTokenExpired(refreshToken)) {
//
//	        String newAccessToken = jwtService.generateAccessToken(id.toString(), farmerOptional.get().getEmail());
//	        return ResponseEntity.ok(new RefreshTokenResponseDto(newAccessToken));
//	    }
//	    
//	    throw new BadCredentialsException(INVALID_TOKEN);
//	}
}
