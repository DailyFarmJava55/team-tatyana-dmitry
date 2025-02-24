package telran.accounting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.accounting.service.UserAccountService;
import telran.dailyFarm.accounting.dto.auth.FarmerRegisterDto;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserRegisterDto;
import telran.dailyFarm.accounting.service.UserService;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor

public class UserAccountController {
	private final UserAccountService userService;
	  @PostMapping("/register/user")
	    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
	        return ResponseEntity.ok(authAccountService.register(userRegisterDto));
	    }

	    @PostMapping("/register/farmer")
	    public ResponseEntity<FarmerDto> registerFarmer(@RequestBody @Valid FarmerRegisterDto farmerRegisterDto) {
	        return ResponseEntity.ok(authAccountService.register(farmerRegisterDto));
	    }

}
