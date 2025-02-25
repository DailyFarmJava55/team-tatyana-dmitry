package telran.auth.account.service.user;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.UserDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
	private final UserRepository userRepository;
    private final JwtService jwtService;
    //private final ModelMapper modelMapper;
    private final RevokedTokenService revokedTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getPassword() == null) {
            throw new InvalidUserDataException("Email and password cannot be null");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists!");
        }

        User newUser = new User(userDto.getEmail(), 
                                passwordEncoder.encode(userDto.getPassword()),
                                userDto.getLanguage() != null ? userDto.getLanguage() : "en",
                                userDto.getLocation());

        newUser.getRoles().add(Role.USER);
        newUser.setTimezone(userDto.getTimezone() != null ? userDto.getTimezone() : "Europe/Berlin");

        userRepository.save(newUser);

        
        Authentication auth = new UsernamePasswordAuthenticationToken(
                newUser.getEmail(), List.of(Role.USER));

        return jwtService.generateToken(auth); 
    }

    @Override
    public String login(Authentication auth) {
		return jwtService.generateToken(auth);
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

    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        return new UserDto(user.getEmail(), "********", user.getLanguage(), user.getTimezone(), user.getLocation());
    }


}