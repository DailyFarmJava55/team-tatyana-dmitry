package telran.accounting.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.accounting.dto.FarmerDto;
import telran.accounting.dto.FarmerRegisterDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserLoginDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.service.UserAccountService;



@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor

public class UserAccountController {
	private final UserAccountService userService;
	  @PostMapping("register/user")
	    public UserDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
	        return userService.registerUser(userRegisterDto);
	    }

	  @PostMapping("register/farmer")
	    public FarmerDto registerFarmer(@RequestBody FarmerRegisterDto farmerRegisterDto) {
	        return userService.registerFarmer(farmerRegisterDto);
	    }
	 
	  @PostMapping("user/login")
	    public UserDto loginUser(@RequestBody UserLoginDto userLoginDto) {
	        return userService.loginUser(userLoginDto);
	    }
	  @PostMapping("farmer/login")
	    public FarmerDto loginFarmer(@RequestBody UserRegisterDto userLoginDto) {
	        return userService.loginFarmer(userLoginDto);
	    }
//	  @PostMapping("user/logout")
//	    public UserDto logoutUser(@RequestBody  UserRegisterDto userRegisterDto) {
//	        return userService.logoutUser(userRegisterDto);
//	    }
//	  @PostMapping("farmer/logout")
//	    public UserDto logoutFarmer(@RequestBody UserRegisterDto userRegisterDto) {
//	        return userService.logoutFarmer(userRegisterDto);
//	    }
}
