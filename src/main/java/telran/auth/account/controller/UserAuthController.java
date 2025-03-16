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
import telran.auth.account.dto.UserDto;
import telran.auth.account.service.user.UserAuthService;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserAuthController {
	private final UserAuthService userAuthService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userDto) {
		AuthResponse response = userAuthService.registerUser(userDto);
		return ResponseEntity.ok(response);
	}

	
	@PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(userAuthService.authenticateUser(request));
    }
	
	@PostMapping("/refresh")
 public ResponseEntity<AuthResponse> refreshAccessToken(@RequestHeader("x-refresh-token") String refreshToken) {
	    AuthResponse response = userAuthService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
		UserDto user = userAuthService.getUser(principal.getName());
		return ResponseEntity.ok(user);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Missing or invalid token");
		}

		String token = authHeader.substring(7);
		userAuthService.logout(token);
		return ResponseEntity.ok("User logged out successfully");
	}

}
