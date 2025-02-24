package telran.accounting.service;

import jakarta.validation.Valid;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserEditDto;
import telran.accounting.dto.UserRegisterDto;

public class UserAccountServiceImpl implements UserAccountService {

	@Override
	public UserDto getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(Long id, UserEditDto userEditDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDto findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String email, String newPassword) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDto registerUser(@Valid UserRegisterDto userRegisterDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
