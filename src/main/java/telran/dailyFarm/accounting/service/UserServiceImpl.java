package telran.dailyFarm.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dao.UserRepository;
import telran.dailyFarm.accounting.dto.exception.UserNotFoundException;
import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserEditDto;
import telran.dailyFarm.accounting.model.Role;
import telran.dailyFarm.accounting.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDto getUserById(Long id) {
		return mapToDto(findUserById(id));
	}

	@Override
	public UserDto updateUser(Long id, UserEditDto userEditDto) {
		User user = findUserById(id);
		if (userEditDto.getUsername() != null) {
			user.setUsername(userEditDto.getUsername());
		}
		if (userEditDto.getEmail() != null) {
			user.setEmail(userEditDto.getEmail());
		}
		userRepository.save(user);
		return mapToDto(user);
	}

	@Override
	public void deleteUser(Long id) {
		User user = findUserById(id);
		if(user.getRole() == Role.FARMER) {
			userRepository.deleteById(id);
		}
		userRepository.delete(user);
	}

	@Override
	public void changePassword(String username, String newPassword) {
		if (newPassword == null || newPassword.trim().isEmpty()) {
	        throw new IllegalArgumentException("Password cannot be empty");
	    }
		User user = userRepository.findUserByUsername(username); 
	    user.setPassword(passwordEncoder.encode(newPassword));
	    userRepository.save(user);
	}

	private User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}

	private UserDto mapToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto findByUsername(String username) {
		User user = userRepository.findByUsername(username)
	            .orElseThrow(UserNotFoundException::new);
	    return mapToDto(user);
	}
	
}
