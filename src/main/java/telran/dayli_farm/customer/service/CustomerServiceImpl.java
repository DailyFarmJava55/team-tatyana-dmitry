package telran.dayli_farm.customer.service;



import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.CustomerRegisterDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;

import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.entity.Customer;
import telran.dayli_farm.entity.CustomerCredential;
import telran.dayli_farm.security.service.AuthService;

import static telran.dayli_farm.api.message.ErrorMessages.CUSTOMER_WITH_THIS_EMAIL_EXISTS;
import static telran.dayli_farm.api.message.ErrorMessages.CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS;;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
	
	private final CustomerRepository customerRepository;
	 private final PasswordEncoder passwordEncoder;
	 private final CustomerCredentialRepository customerCredentialRepository; 
	 private final AuthService authService;

	@Override
	public ResponseEntity<CustomerRegisterDto> registerCustomer(@Valid CustomerRegisterDto customerRegisterDto) {
		checkEmail(customerRegisterDto.getEmail());
		Customer customer = Customer.of(customerRegisterDto);
		customerRepository.save(customer);
		
		CustomerCredential credential = CustomerCredential.builder()
                .createdAt(LocalDateTime.now())
                .passwordLastUpdated(LocalDateTime.now())
                .hashedPassword(passwordEncoder.encode(customerRegisterDto.getPassword()))
                .customer(customer)
                .build();
		customerCredentialRepository.save(credential);
        
		return ResponseEntity.status(HttpStatus.CREATED).body(customerRegisterDto);
	}

	private void checkEmail(String email) {
		if (customerRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, CUSTOMER_WITH_THIS_EMAIL_EXISTS);
		}
	}

	@Override
	public ResponseEntity<TokenResponseDto> loginCustomer(@Valid LoginRequestDto loginRequestDto) {
		String email =loginRequestDto.getEmail();
		
		customerRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS ));
		
		TokenResponseDto tokens = authService.authenticate(email, loginRequestDto.getPassword());
		 
		return ResponseEntity.ok(tokens);
	}

	@Override
	public ResponseEntity<Customer> getUserByEmail(String name) {
		 Customer customer = customerRepository.findByEmail(name).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS));
		return ResponseEntity.ok(customer);
	}

}
