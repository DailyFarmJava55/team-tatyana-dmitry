package telran.auth.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.service.farm.FarmService;

@RestController
@RequestMapping("/api/auth/farmer")
@RequiredArgsConstructor
public class FarmAuthController {

	private final FarmService farmAccountService;
	
	@PostMapping("/register")
    public ResponseEntity<String> registerFarmer(@RequestBody FarmerDto farmerDto) {
		farmAccountService.registerFarmer(farmerDto);
        return ResponseEntity.ok("Farmer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginFarmer(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(farmAccountService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutFarmer(@RequestParam String email) {
    	farmAccountService.logout(email);
        return ResponseEntity.ok("Farmer logged out successfully");
    }
}
