package telran.auth.account.service.user;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.UserDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
    private final JwtService jwtService;
    //private final ModelMapper modelMapper;
    private final RevokedTokenService revokedTokenService;

    @Override
    public void registerUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getPassword() == null) {
            throw new InvalidUserDataException("Email and password cannot be null");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists!");
        }

        User newUser = new User(userDto.getEmail(), userDto.getPassword(), 
                                userDto.getLanguage() != null ? userDto.getLanguage() : "en",
                                userDto.getLocation());

        newUser.getRoles().add(Role.USER);
        newUser.setTimezone(userDto.getTimezone() != null ? userDto.getTimezone() : "Europe/Berlin");

        userRepository.save(newUser);
    }

    @Override
    public AuthResponse login(AuthRequestDto request) {
        User user = findUserByEmail(request.getEmail());
        validatePassword(request.getPassword(), user.getPassword());

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(user.getEmail(), user.getRoles(), token);
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            revokedTokenService.revokeToken(token);
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!User.checkPassword(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

}