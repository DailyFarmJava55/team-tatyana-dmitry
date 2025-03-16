package telran.auth.account.service.user;

import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;

public interface UserAuthService {
	
	AuthResponse registerUser(UserDto userDto);
	
    void logout(String email);

	UserDto getUser(String name);

AuthResponse	authenticateUser(AuthRequestDto request);

AuthResponse refreshAccessToken(String refreshToken);

}
