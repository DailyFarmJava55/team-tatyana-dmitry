package telran.auth.account.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.service.farm.FarmAuthService;

@RestController
@RequestMapping("/api/auth/farmer")
@RequiredArgsConstructor
public class FarmAuthController {

	private final FarmAuthService farmAuthService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerFarmer(@Valid @RequestBody FarmerDto farmerDto) {

		AuthResponse response = farmAuthService.registerFarmer(farmerDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")

    public ResponseEntity<AuthResponse> loginFarmer(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(farmAuthService.authenticateFarmer(request));
    }
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
	  //  refreshToken = refreshToken.substring(7);beaver 
	    System.out.println("refreshToken: " + refreshToken);
	    AuthResponse response = farmAuthService.refreshAccessToken(refreshToken);
	    return ResponseEntity.ok(response);
	}

	@GetMapping("/me")
	public ResponseEntity<FarmerDto> getCurrentFarmer(Principal principal) {
		FarmerDto farmer = farmAuthService.getFarmer(principal.getName());
		return ResponseEntity.ok(farmer);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logoutFarmer(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Missing or invalid token");
		}

		String token = authHeader.substring(7);
		farmAuthService.logout(token);
		return ResponseEntity.ok("Farmer logged out successfully");
	}
}
