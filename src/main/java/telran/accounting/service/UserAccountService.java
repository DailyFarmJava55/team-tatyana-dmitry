package telran.accounting.service;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import telran.accounting.dto.FarmerDto;
import telran.accounting.dto.FarmerRegisterDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserLoginDto;
import telran.accounting.dto.UserRegisterDto;

@Service
public interface UserAccountService {

	UserDto registerUser(@Valid UserRegisterDto userRegisterDto);

	FarmerDto registerFarmer(FarmerRegisterDto farmerRegisterDto);

	UserDto loginUser(UserLoginDto userLoginDto);

	FarmerDto loginFarmer(UserRegisterDto userLoginDto);

}
