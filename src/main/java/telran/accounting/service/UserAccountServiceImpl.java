package telran.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserEditDto;
import telran.accounting.dto.UserRegisterDto;
import telran.java55.accounting.dao.UserAccountRepository;


@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
	
	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;
	final PasswordEncoder passwordEncoder;
	
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
		if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException();
		}
		return null;
	}

}
