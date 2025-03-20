package telran.dayli_farm.customer.service;

import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import telran.dayli_farm.api.dto.CustomerRegisterDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.entity.Customer;

public interface ICustomerService {

	ResponseEntity<CustomerRegisterDto> registerCustomer(@Valid CustomerRegisterDto customerRegisterDto);

	ResponseEntity<TokenResponseDto> loginCustomer(@Valid LoginRequestDto loginRequestDto);

	ResponseEntity<Customer> getUserByEmail(String name);

}
