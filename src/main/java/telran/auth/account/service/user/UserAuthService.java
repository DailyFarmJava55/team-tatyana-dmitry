package telran.auth.account.service.user;

import org.springframework.security.core.Authentication;

import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.model.User;

public interface UserAuthService {
	
	AuthResponse registerUser(UserDto userDto);
	
    void logout(String email);

	User findUserByEmail(String email);

	UserDto getUser(String name);

	String login(Authentication auth);


}
