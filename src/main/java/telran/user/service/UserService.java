package telran.user.service;

import java.util.List;
import java.util.UUID;

import telran.auth.account.model.User;
import telran.user.dto.UserUpdateRequest;

public interface UserService {

	List<User> getAllUsers();

	User getUserById(UUID id);

	User updateUser(UUID id, UserUpdateRequest request);

	void deleteUser(UUID id);

	User getUserByEmail(String email);

}
