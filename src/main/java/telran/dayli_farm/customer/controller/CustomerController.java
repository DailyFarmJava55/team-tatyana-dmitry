package telran.dayli_farm.customer.controller;

import static telran.dayli_farm.api.ApiConstants.*;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.dayli_farm.api.dto.CustomerRegisterDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.service.ICustomerService;
import telran.dayli_farm.entity.Customer;
import telran.dayli_farm.security.service.AuthService;

@Tag(name = "Customer API", description = "Methods for customers")
@RestController

@RequiredArgsConstructor
public class CustomerController {
	private final ICustomerService customerService;
	private final AuthService authService;
	

	@PostMapping(CUSTOMER_REGISTER)
	public ResponseEntity<CustomerRegisterDto> registerCustomer(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
		return customerService.registerCustomer(customerRegisterDto);
	}

	@PostMapping(CUSTOMER_LOGIN)
	public ResponseEntity<TokenResponseDto> loginCustomer(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		return customerService.loginCustomer(loginRequestDto);
	}
	
	@PostMapping(CUSTOMER_REFRESH_TOKEN )
    public ResponseEntity<RefreshTokenResponseDto> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }

	@GetMapping(CUSTOMER_CURRENT)
	public ResponseEntity<Customer> getCurrentCustomer(Principal principal) {
	return customerService.getUserByEmail(principal.getName());
	}

//	@PostMapping(CUSTOMER_LOGOUT)
//	public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String authHeader) {
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//			return ResponseEntity.badRequest().body("Missing or invalid token");
//		}
//
//		String token = authHeader.substring(7);
//		authService.logout(token);
//		return ResponseEntity.ok("User logged out successfully");
//	}

}
