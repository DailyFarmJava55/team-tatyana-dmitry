package telran.dayli_farm.customer.service;

import static telran.dayli_farm.api.message.ErrorMessages.CUSTOMER_WITH_THIS_EMAIL_EXISTS;
import static telran.dayli_farm.api.message.ErrorMessages.CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS;
import static telran.dayli_farm.api.message.ErrorMessages.OLD_PASSWORD_IS_NOT_CORECT;
import static telran.dayli_farm.api.message.ErrorMessages.USER_NOT_FOUND;
import static telran.dayli_farm.api.message.SuccessMessages.USER_DELETED_SUCCESSFULLY;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.CustomerDto;
import telran.dayli_farm.api.dto.CustomerEditDto;
import telran.dayli_farm.api.dto.CustomerRegisterDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.entity.Customer;
import telran.dayli_farm.entity.CustomerCredential;
import telran.dayli_farm.security.service.AuthService;
import telran.dayli_farm.security.service.RevokedTokenService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerCredentialRepository customerCredentialRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;
	//private final JwtService jwtService;
	private final RevokedTokenService revokedTokenService;;

	@Override
	@Transactional
	public ResponseEntity<CustomerDto> registerCustomer(@Valid CustomerRegisterDto customerRegisterDto) {
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
        
		return ResponseEntity.status(HttpStatus.CREATED).body(CustomerDto.of(customer));
	}

	private void checkEmail(String email) {
		if (customerRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, CUSTOMER_WITH_THIS_EMAIL_EXISTS);
		}
	}

	@Override
	public ResponseEntity<TokenResponseDto> loginCustomer(@Valid LoginRequestDto loginRequestDto) {
		String email = loginRequestDto.getEmail();

		Customer customer = customerRepository.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS));
		customer.getCredential().setLastLogin(LocalDateTime.now());
		
		TokenResponseDto tokens = authService.authenticate(email, loginRequestDto.getPassword());
		
		return ResponseEntity.ok(tokens);
	}

	@Override
	public ResponseEntity<Customer> getUserByEmail(String name) {
		Customer customer = customerRepository.findByEmail(name).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS));
		return ResponseEntity.ok(customer);
	}

	@Override
	@Transactional
	public ResponseEntity<String> removeCustomerById(UUID id) {
		Customer customer = customerRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS));
		customerRepository.delete(customer);

		return ResponseEntity.ok(USER_DELETED_SUCCESSFULLY);
	}

	@Override
	@Transactional
	public ResponseEntity<CustomerDto> updateCustomer(UUID id, @Valid CustomerEditDto customerEditDto) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
		customer.setFirstName(customerEditDto.getFirstName());
		customer.setLastName(customerEditDto.getLastName());
		log.info("Service.updateCustomer(). Name updated");
		customer.setEmail(customerEditDto.getEmail());
		log.info("Service.updateCustomer(). Email updated");
		customer.setPhone(customerEditDto.getPhone());
		log.info("Service.updateCustomer(). Phone updated");
		return ResponseEntity.ok(CustomerDto.of(customer));
	}

	@Override
	public ResponseEntity<String> logoutCustomer(UUID id, String token) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
		CustomerCredential credential = customerCredentialRepository.findByCustomer(customer);
		token = token.substring(7);
		revokedTokenService.addToBlackList(token);
		credential.setRefreshToken("");
		return ResponseEntity.ok("Logout successful");
	}

	@Override
	@Transactional
	public ResponseEntity<TokenResponseDto> updatePassword(UUID id, @Valid ChangePasswordRequestDto changePasswordDto) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
		CustomerCredential credential = customerCredentialRepository.findByCustomer(customer);

		String oldPw = credential.getHashedPassword();

		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), oldPw)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OLD_PASSWORD_IS_NOT_CORECT);
		}
		credential.setHashedPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		credential.setPasswordLastUpdated(LocalDateTime.now());

		TokenResponseDto tokens = authService.authenticate(customer.getEmail(), changePasswordDto.getNewPassword());

		return ResponseEntity.ok(tokens);
	}

}
