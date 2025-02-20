package telran.dailyFarm.accounting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.dailyFarm.accounting.dto.auth.FarmerRegisterDto;
import telran.dailyFarm.accounting.dto.auth.LoginDto;
import telran.dailyFarm.accounting.dto.auth.LoginRequestDto;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserRegisterDto;
@Service
@Transactional
public interface AuthAccountService {

	UserDto register(UserRegisterDto userRegisterDto);
	FarmerDto register(FarmerRegisterDto farmerRegisterDto);
	
	
	LoginDto login(LoginRequestDto loginDto);
    void logout();
}
