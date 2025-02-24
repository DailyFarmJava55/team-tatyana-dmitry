package telran.auth.account.service.user;

import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.model.User;

public interface UserService {
	
	void registerUser(UserDto userDto);
	
	AuthResponse login(AuthRequestDto request);
    
    void logout(String email);

	User findUserByEmail(String email);


}
