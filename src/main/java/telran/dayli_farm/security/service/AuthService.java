package telran.dayli_farm.security.service;

import static telran.dayli_farm.api.message.ErrorMessages.WRONG_USER_NAME_OR_PASSWORD;

import java.util.Optional;

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
	public ResponseEntity<RefreshTokenResponseDto> refreshAccessToken(String refreshToken) {
		//TODO: Implement this method
		return null;
		
	}
}
