package telran.dayli_farm.customer.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.CustomerDto;
import telran.dayli_farm.api.dto.CustomerRegisterDto;
import telran.dayli_farm.api.dto.CustomerEditDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.entity.Customer;

public interface ICustomerService {

	ResponseEntity<CustomerDto> registerCustomer(@Valid CustomerRegisterDto customerRegisterDto);

	ResponseEntity<TokenResponseDto> loginCustomer(@Valid LoginRequestDto loginRequestDto);

	ResponseEntity<Customer> getUserByEmail(String email);

	ResponseEntity<String> removeCustomerById(UUID id);

	ResponseEntity<CustomerDto> updateCustomer(UUID id, @Valid CustomerEditDto customerEditDto);

	ResponseEntity<String> logoutCustomer(UUID id, String token);

	ResponseEntity<TokenResponseDto> updatePassword(UUID id, @Valid ChangePasswordRequestDto changePasswordDto);

}
