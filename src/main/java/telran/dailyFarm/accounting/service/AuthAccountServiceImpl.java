package telran.dailyFarm.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class AuthAccountServiceImpl implements AuthAccountService {
	final ModelMapper modelMapper;
	final PasswordEncoder passwordEncoder;
	final UserRepository userRepository;
	final FarmerRepository farmerRepository;

	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {
		userRepository.findByUsername(userRegisterDto.getUsername()).ifPresent(user -> {
			throw new UserExistsException();
		});
		validateAdminCreation(userRegisterDto.getRole());

		User user = modelMapper.map(userRegisterDto, User.class);
		user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		user.setRole(Role.valueOf(userRegisterDto.getRole().toUpperCase()));

		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public FarmerDto register(FarmerRegisterDto farmerRegisterDto) {
		farmerRepository.findByUsername(farmerRegisterDto.getUsername()).ifPresent(farmer -> {
			throw new UserExistsException();
		});

		Farmer farmer = modelMapper.map(farmerRegisterDto, Farmer.class);
		farmer.setPassword(passwordEncoder.encode(farmerRegisterDto.getPassword()));
		farmer.setRole(Role.FARMER);

		farmer.setAddress(modelMapper.map(farmerRegisterDto.getAddress(), Address.class));
		farmer.setLocation(modelMapper.map(farmerRegisterDto.getLocation(), Location.class));

		farmerRepository.save(farmer);

		FarmerDto farmerDto = modelMapper.map(farmer, FarmerDto.class);
		farmerDto.setAddress(modelMapper.map(farmer.getAddress(), AddressDto.class));
		farmerDto.setLocation(modelMapper.map(farmer.getLocation(), LocationDto.class));
		return farmerDto;
	}

	@Override
	public LoginDto login(LoginRequestDto loginRequestDto) {
	    String email = loginRequestDto.getEmail();
	    String password = loginRequestDto.getPassword();

	    Object foundUser = userRepository.findByEmail(email)
	            .map(user -> (Object) user) 
	            .orElseGet(() -> farmerRepository.findByEmail(email)
	            .map(farmer -> (Object) farmer) 
	            .orElseThrow(UserNotFoundException::new));

	    String storedPassword = (foundUser instanceof User) 
	            ? ((User) foundUser).getPassword() 
	            : ((Farmer) foundUser).getPassword();

	    if (!passwordEncoder.matches(password, storedPassword)) {
	        throw new BadCredentialsException("Invalid credentials");
	    }

	    return modelMapper.map(foundUser, LoginDto.class);
	}


	@Override
	public void logout() {
		System.out.println("Logout called, but no token management yet.");
		// TODO Auto-generated method stub

	}

	private void validateAdminCreation(String role) {
		if ("ADMIN".equalsIgnoreCase(role)) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean isNotAdmin = authentication == null || authentication.getAuthorities().stream()
					.noneMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));

			if (isNotAdmin) {
				throw new AccessDeniedException("Only an admin can create another admin.");
			}
		}

	}

}
