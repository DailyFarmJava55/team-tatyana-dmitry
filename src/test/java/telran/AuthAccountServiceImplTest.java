package telran;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.dailyFarm.accounting.dao.FarmerRepository;
import telran.dailyFarm.accounting.dao.UserRepository;
import telran.dailyFarm.accounting.dto.auth.FarmerRegisterDto;
import telran.dailyFarm.accounting.dto.auth.LoginDto;
import telran.dailyFarm.accounting.dto.auth.LoginRequestDto;
import telran.dailyFarm.accounting.dto.exception.UserExistsException;
import telran.dailyFarm.accounting.dto.exception.UserNotFoundException;
import telran.dailyFarm.accounting.dto.farmer.AddressDto;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.farmer.LocationDto;
import telran.dailyFarm.accounting.dto.user.UserDto;
import telran.dailyFarm.accounting.dto.user.UserRegisterDto;
import telran.dailyFarm.accounting.model.Role;
import telran.dailyFarm.accounting.model.User;
import telran.dailyFarm.accounting.model.farmer.Address;
import telran.dailyFarm.accounting.model.farmer.Farmer;
import telran.dailyFarm.accounting.model.farmer.Location;
import telran.dailyFarm.accounting.service.AuthAccountServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthAccountServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private FarmerRepository farmerRepository;
    
    @InjectMocks
    private AuthAccountServiceImpl authAccountService;

    private UserRegisterDto userRegisterDto;
    private FarmerRegisterDto farmerRegisterDto;
    private User user;
    private Farmer farmer;

    @BeforeEach
    void setUp() {
       
        AddressDto addressDto = new AddressDto("USA", "New York", "Main Street", "10A", "12345");
        LocationDto locationDto = new LocationDto(40.7128, -74.0060);
         userRegisterDto = new UserRegisterDto("testUser", "testuser@mail.com","password", "USER");
        farmerRegisterDto = new FarmerRegisterDto(
            "farmerUser", "testfarmer@mail.com", "password", "FARMER",
            "Happy Farm", addressDto, locationDto
        );

        user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);
        
        farmer = new Farmer();
        farmer.setUsername("farmerUser");
        farmer.setPassword("encodedPassword");
        farmer.setRole(Role.FARMER);
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByUsername(userRegisterDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRegisterDto.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(userRegisterDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(new UserDto(1L, "testUser", "testuser@mail.com", "USER"));

        UserDto result = authAccountService.register(userRegisterDto);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    void registerUser_UserExists() {
        when(userRepository.findByUsername(userRegisterDto.getUsername())).thenReturn(Optional.of(user));
        assertThrows(UserExistsException.class, () -> authAccountService.register(userRegisterDto));
    }

    @Test
    void registerFarmer_Success() {
        when(farmerRepository.findByUsername(farmerRegisterDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(farmerRegisterDto.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(farmerRegisterDto, Farmer.class)).thenReturn(farmer);
        when(farmerRepository.save(farmer)).thenReturn(farmer);
        
        doReturn(new FarmerDto()).when(modelMapper).map(any(Farmer.class), eq(FarmerDto.class));
        doReturn(new Address()).when(modelMapper).map(any(AddressDto.class), eq(Address.class));
        doReturn(new Location()).when(modelMapper).map(any(LocationDto.class), eq(Location.class));
        doReturn(new AddressDto()).when(modelMapper).map(any(Address.class), eq(AddressDto.class));
        doReturn(new LocationDto()).when(modelMapper).map(any(Location.class), eq(LocationDto.class));

        assertNotNull(authAccountService.register(farmerRegisterDto));
    }

    @Test
    void registerFarmer_UserExists() {
        when(farmerRepository.findByUsername(farmerRegisterDto.getUsername())).thenReturn(Optional.of(farmer));
        assertThrows(UserExistsException.class, () -> authAccountService.register(farmerRegisterDto));
    }

    @Test
    void login_Success() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("testUser@mail.com", "password");
        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())).thenReturn(true);
        when(modelMapper.map(user, LoginDto.class)).thenReturn(new LoginDto("testUser","testuser@mail.com",Role.USER));

        LoginDto result = authAccountService.login(loginRequestDto);
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void login_UserNotFound() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("notfound@mail.com", "password");
        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.empty());
        when(farmerRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authAccountService.login(loginRequestDto));
    }

    @Test
    void login_InvalidPassword() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("testUser@mail.com", "wrongPassword");
        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authAccountService.login(loginRequestDto));
    }
}

