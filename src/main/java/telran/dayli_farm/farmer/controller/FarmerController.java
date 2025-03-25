package telran.dayli_farm.farmer.controller;
import static telran.dayli_farm.api.ApiConstants.FARMER_CHANGE_PASSWORD;
import static telran.dayli_farm.api.ApiConstants.FARMER_CURRENT;
import static telran.dayli_farm.api.ApiConstants.FARMER_EDIT;
import static telran.dayli_farm.api.ApiConstants.FARMER_LOGIN;
import static telran.dayli_farm.api.ApiConstants.FARMER_LOGOUT;
import static telran.dayli_farm.api.ApiConstants.FARMER_REFRESH_TOKEN;
import static telran.dayli_farm.api.ApiConstants.FARMER_REGISTER;
import static telran.dayli_farm.api.ApiConstants.FARMER_REMOVE;

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
import telran.dayli_farm.farmer.dto.FarmerDto;
import telran.dayli_farm.farmer.dto.FarmerEditDto;
import telran.dayli_farm.farmer.dto.FarmerRegisterDto;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.service.IFarmerService;
import telran.dayli_farm.security.CustomUserDetails;
import telran.dayli_farm.security.service.AuthService;

@Tag(name = "Farmer API", description = "Methods for farmer")
@RestController
@RequiredArgsConstructor
public class FarmerController {
	private final IFarmerService farmerService;
	private final AuthService authService;
	
	@PostMapping(FARMER_REGISTER)
	public ResponseEntity<FarmerDto> registerFarmer(@Valid @RequestBody FarmerRegisterDto farmerRegisterDto) {
		return farmerService.registerFarmer(farmerRegisterDto);
	}

	@PostMapping(FARMER_LOGIN)
	public ResponseEntity<TokenResponseDto> loginFarmer(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		return farmerService.loginFarmer(loginRequestDto);
	}
	
	@PostMapping(FARMER_REFRESH_TOKEN )
    public ResponseEntity<RefreshTokenResponseDto> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
        return authService.refreshFarmerAccessToken(refreshToken);
    }

	@GetMapping(FARMER_CURRENT)
	public ResponseEntity<Farmer> getCurrentFarmer(Principal principal) {
	return farmerService.getFarmerByEmail(principal.getName());
	}
	
	@PutMapping(FARMER_EDIT)
    @PreAuthorize("hasRole(FARMER)")
    public ResponseEntity<FarmerDto> updateFarmer(@Valid @RequestBody FarmerEditDto farmerEditDto,@AuthenticationPrincipal CustomUserDetails user) {
        return farmerService.updateFarmer(user.getId(), farmerEditDto);
    }
	
	@PutMapping(FARMER_CHANGE_PASSWORD)
    @PreAuthorize("hasRole(FARMER)")
    public ResponseEntity<TokenResponseDto> farmerUpdatePassword(
            @Valid @RequestBody ChangePasswordRequestDto changePasswordDto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return farmerService.updatePassword(user.getId(), changePasswordDto);
    }

	@DeleteMapping(FARMER_REMOVE)
    @PreAuthorize("hasRole(FARMER)")
    public ResponseEntity<String> removeFarmer(@AuthenticationPrincipal CustomUserDetails user) {
        return farmerService.removeFarmerById(user.getId());
    }
	
	@DeleteMapping(FARMER_LOGOUT)
    public ResponseEntity<String> logoutFarmer(@AuthenticationPrincipal CustomUserDetails user, @RequestHeader("Authorization") String token) {
        return farmerService.logoutFarmer(user.getId(), token);
    }
}
