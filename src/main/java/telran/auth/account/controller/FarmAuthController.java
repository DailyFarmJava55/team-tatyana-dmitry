package telran.auth.account.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.LoginRequest;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.account.service.farm.FarmAuthService;
import telran.auth.security.JwtService;

@RestController
@RequestMapping("/api/auth/farmer")
@RequiredArgsConstructor
public class FarmAuthController {

	private final FarmAuthService farmAuthService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerFarmer(@Valid @RequestBody FarmerDto farmerDto) {
		String token = farmAuthService.registerFarmer(farmerDto);
		return ResponseEntity.ok(new AuthResponse(farmerDto.getId(), farmerDto.getEmail(), Set.of(Role.FARMER), token));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtService.generateToken(authentication);
		User farmer = farmAuthService.findFarmerByEmail(loginRequest.getEmail());
		return ResponseEntity.ok(new AuthResponse(farmer.getId(), farmer.getEmail(), farmer.getRoles(), token)); 
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
