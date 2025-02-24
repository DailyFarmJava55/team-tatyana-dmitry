package telran.auth.account.service.farm;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.exceptions.InvalidUserDataException;
import telran.auth.account.dto.exceptions.UserAlreadyExistsException;
import telran.auth.account.model.Role;
import telran.auth.account.model.User;
import telran.auth.security.JwtService;
import telran.auth.security.RevokedTokenService;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final RevokedTokenService revokedTokenService;

    @Override
    public void registerFarmer(FarmerDto farmerDto) {
        if (farmerDto.getEmail() == null || farmerDto.getPassword() == null) {
            throw new InvalidUserDataException("Email and password cannot be null");
        }

        User user = userRepository.findByEmail(farmerDto.getEmail()).orElse(null);

        if (user != null) {
            if (user.getRoles().contains(Role.FARMER)) {
                throw new UserAlreadyExistsException("Farmer with this email already exists!");
            }
            user.getRoles().add(Role.FARMER);
            modelMapper.map(farmerDto, user);
        } else {
            user = new User(farmerDto.getEmail(), farmerDto.getPassword(),
                            farmerDto.getLanguage() != null ? farmerDto.getLanguage() : "en",
                            farmerDto.getLocation());
            user.getRoles().add(Role.FARMER);
        }

        user.setTimezone(farmerDto.getTimezone() != null ? farmerDto.getTimezone() : "Europe/Berlin");
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequestDto request) {
        User user = findFarmerByEmail(request.getEmail());
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

    public User findFarmerByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getRoles().contains(Role.FARMER))
                .orElseThrow(() -> new UsernameNotFoundException("Farmer not found"));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!User.checkPassword(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
