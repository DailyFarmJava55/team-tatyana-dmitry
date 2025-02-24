package telran.auth.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.model.User;
import telran.auth.account.service.user.UserService;
import telran.auth.security.JwtService;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserAuthController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
		userService.registerUser(userDto);
		return ResponseEntity.ok("User registered successfully!");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestHeader String email, @RequestHeader String password) {
		 authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(email, password)
	        );

	        User user = userService.findUserByEmail(email);
	        String token = jwtService.generateToken(user.getEmail());

	        return ResponseEntity.ok(new AuthResponse(user.getEmail(), user.getRoles(), token));
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser(@RequestParam String email) {
		userService.logout(email);
		return ResponseEntity.ok("User logged out successfully");
	}

}
