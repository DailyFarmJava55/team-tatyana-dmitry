package telran.accounting.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.service.UserAccountService;



@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor

public class UserAccountController {
	private final UserAccountService userService;
	  @PostMapping("register/user")
	    public UserDto registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
	        return userService.registerUser(userRegisterDto);
	    }

	  

}
