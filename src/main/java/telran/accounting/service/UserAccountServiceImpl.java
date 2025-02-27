package telran.accounting.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.accounting.dao.UserAccountRepository;
import telran.accounting.dto.FarmerDto;
import telran.accounting.dto.FarmerRegisterDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserLoginDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.exception.UserExistsException;
import telran.accounting.model.Location;
import telran.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;
	final PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerUser(@Valid UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsByEmail(userRegisterDto.getEmail())) {
			throw new UserExistsException();
		}

		UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
		String password = passwordEncoder.encode(userRegisterDto.getPassword());
		userAccount.setPassword(password);
//		userAccount.setPassword(userRegisterDto.getPassword());
		userAccount.addRole("USER");
		userAccount.setRegistrationDate(LocalDateTime.now());
		userAccount.setLastEditDate(LocalDateTime.now());
		userAccount.setLastAccessDate(LocalDateTime.now());
		userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, UserDto.class);

	}

	@Override
	public FarmerDto registerFarmer(FarmerRegisterDto farmerRegisterDto) {
		if (userAccountRepository.existsByEmail(farmerRegisterDto.getEmail())) {
			throw new UserExistsException();
		}
		UserAccount userAccount = modelMapper.map(farmerRegisterDto, UserAccount.class);
		String password = passwordEncoder.encode(farmerRegisterDto.getPassword());
		userAccount.setPassword(password);
//			userAccount.setPassword(farmerRegisterDto.getPassword());
		userAccount.addRole("FARMER");
		userAccount.setRegistrationDate(LocalDateTime.now());
		userAccount.setLastEditDate(LocalDateTime.now());
		userAccount.setLastAccessDate(LocalDateTime.now());
		Location location = new Location(farmerRegisterDto.getLocation().getLatitude(),
				farmerRegisterDto.getLocation().getLongitude());
		userAccount.setLocation(location);

		userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, FarmerDto.class);
	}

	@Override
	public UserDto loginUser(UserLoginDto userLoginDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarmerDto loginFarmer(UserRegisterDto userLoginDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
