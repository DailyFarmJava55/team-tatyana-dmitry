package telran.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.User;
import telran.user.dto.UserUpdateRequest;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Override
	public User updateUser(UUID id, UserUpdateRequest request) {
		 User user = getUserById(id);
	        if (request.getEmail() != null) user.setEmail(request.getEmail());
	        return userRepository.save(user);
	}

	@Override
	public void deleteUser(UUID id) {
		 userRepository.deleteById(id);

	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				  .orElseThrow(() -> new UserNotFoundException("User not found"));
	}

}
