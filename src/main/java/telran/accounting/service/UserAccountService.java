package telran.accounting.service;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserEditDto;
import telran.accounting.dto.UserRegisterDto;


@Service
public interface UserAccountService {
	UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserEditDto userEditDto);

	void deleteUser(Long id);

	UserDto findByUsername(String username);
	
	void changePassword(String email, String newPassword);

	UserDto registerUser(@Valid UserRegisterDto userRegisterDto);
	
}
