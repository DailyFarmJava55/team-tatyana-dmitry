package telran.dailyFarm.accounting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dto.auth.FarmerRegisterDto;
import telran.dailyFarm.accounting.dto.auth.LoginDto;
import telran.dailyFarm.accounting.dto.auth.LoginRequestDto;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserRegisterDto;
import telran.dailyFarm.accounting.service.AuthAccountService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthAccountService authAccountService;
	
	  @PostMapping("/register/user")
	    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
	        return ResponseEntity.ok(authAccountService.register(userRegisterDto));
	    }

	    @PostMapping("/register/farmer")
	    public ResponseEntity<FarmerDto> registerFarmer(@RequestBody @Valid FarmerRegisterDto farmerRegisterDto) {
	        return ResponseEntity.ok(authAccountService.register(farmerRegisterDto));
	    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
    	LoginDto authResponse = authAccountService.login(loginDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authAccountService.logout();
        return ResponseEntity.ok("Logged out successfully");
    }

}
