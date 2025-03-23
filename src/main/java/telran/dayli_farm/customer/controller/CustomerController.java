package telran.dayli_farm.customer.controller;

import static telran.dayli_farm.api.ApiConstants.CUSTOMER_CHANGE_PASSWORD;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_CURRENT;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_EDIT;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_LOGIN;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_LOGOUT;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_REFRESH_TOKEN;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_REGISTER;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_REMOVE;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.RefreshTokenResponseDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.customer.dto.CustomerDto;
import telran.dayli_farm.customer.dto.CustomerEditDto;
import telran.dayli_farm.customer.dto.CustomerRegisterDto;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.service.ICustomerService;
import telran.dayli_farm.security.CustomUserDetailService;
import telran.dayli_farm.security.service.AuthService;

@Tag(name = "Customer API", description = "Methods for customers")
@RestController

@RequiredArgsConstructor
public class CustomerController {
	private final ICustomerService customerService;
	private final AuthService authService;
	

	@PostMapping(CUSTOMER_REGISTER)
	public ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
		return customerService.registerCustomer(customerRegisterDto);
	}

	@PostMapping(CUSTOMER_LOGIN)
	public ResponseEntity<TokenResponseDto> loginCustomer(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		return customerService.loginCustomer(loginRequestDto);
	}
	
	@PostMapping(CUSTOMER_REFRESH_TOKEN )
    public ResponseEntity<RefreshTokenResponseDto> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
        return authService.refreshCustomerAccessToken(refreshToken);
    }

	@GetMapping(CUSTOMER_CURRENT)
	public ResponseEntity<Customer> getCurrentCustomer(Principal principal) {
	return customerService.getUserByEmail(principal.getName());
	}
	
	@PutMapping(CUSTOMER_EDIT)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerEditDto customerEditDto,@AuthenticationPrincipal CustomUserDetailService user) {
        return customerService.updateCustomer(user.getId(), customerEditDto);
    }
	
	@PutMapping(CUSTOMER_CHANGE_PASSWORD)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<TokenResponseDto> customerUpdatePassword(
            @Valid @RequestBody ChangePasswordRequestDto changePasswordDto,
            @AuthenticationPrincipal CustomUserDetailService user) {
        return customerService.updatePassword(user.getId(), changePasswordDto);
    }

	@DeleteMapping(CUSTOMER_REMOVE)
    @PreAuthorize("hasRole(ROLE_CUSTOMER)")
    public ResponseEntity<String> removeCustomer(@AuthenticationPrincipal CustomUserDetailService user) {
        return customerService.removeCustomerById(user.getId());
    }
	
	@DeleteMapping(CUSTOMER_LOGOUT)
    public ResponseEntity<String> logoutCustomer(@AuthenticationPrincipal CustomUserDetailService user, @RequestHeader("Authorization") String token) {
        return customerService.logoutCustomer(user.getId(), token);
    }

}
