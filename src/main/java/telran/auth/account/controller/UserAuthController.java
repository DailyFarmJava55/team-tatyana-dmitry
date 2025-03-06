package telran.auth.account.controller;

import java.security.Principal;

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
import telran.auth.account.dto.LoginRequest;
import telran.auth.account.dto.UserDto;
import telran.auth.account.model.User;
import telran.auth.account.service.user.UserAuthService;
import telran.auth.security.JwtService;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserAuthController {
	private final UserAuthService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userDto) {

		String token = userService.registerUser(userDto);
		return ResponseEntity.ok(new AuthResponse(null, userDto.getEmail(), Set.of(Role.USER), token));

	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtService.generateToken(authentication);

		User user = userService.findUserByEmail(loginRequest.getEmail());

		return ResponseEntity.ok(new AuthResponse(null, user.getEmail(), user.getRoles(), token));

	}

	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
		UserDto user = userService.getUser(principal.getName());
		return ResponseEntity.ok(user);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Missing or invalid token");
		}

		String token = authHeader.substring(7);
		userService.logout(token);
		return ResponseEntity.ok("User logged out successfully");
	}

}
