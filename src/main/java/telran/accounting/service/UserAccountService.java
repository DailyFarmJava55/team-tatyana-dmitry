package telran.accounting.service;

import org.springframework.stereotype.Service;

import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserEditDto;

@Service
public interface UserAccountService {
	UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserEditDto userEditDto);

	void deleteUser(Long id);

	UserDto findByUsername(String username);
	
	void changePassword(String email, String newPassword);
	
}
