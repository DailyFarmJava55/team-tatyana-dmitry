package telran.dayli_farm.farmer.service;

import static telran.dayli_farm.api.message.ErrorMessages.FARMER_NOT_FOUND;
import static telran.dayli_farm.api.message.ErrorMessages.FARMER_WITH_THIS_EMAIL_EXISTS;
import static telran.dayli_farm.api.message.ErrorMessages.FARMER_WITH_THIS_EMAIL_IS_NOT_EXISTS;
import static telran.dayli_farm.api.message.ErrorMessages.OLD_PASSWORD_IS_NOT_CORECT;
import static telran.dayli_farm.api.message.SuccessMessages.FARMER_DELETED_SUCCESSFULLY;
import static telran.dayli_farm.api.message.SuccessMessages.LOGOUT_SUCCESS;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.farmer.dao.CoordinatesRepository;
import telran.dayli_farm.farmer.dao.FarmerCredentialRepository;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.farmer.dto.FarmerDto;
import telran.dayli_farm.farmer.dto.FarmerEditDto;
import telran.dayli_farm.farmer.dto.FarmerRegisterDto;
import telran.dayli_farm.farmer.entity.Coordinates;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;
import telran.dayli_farm.security.service.AuthService;
import telran.dayli_farm.security.service.RevokedTokenService;
@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements IFarmerService {
	
	private final FarmerRepository farmerRepository;
	private final FarmerCredentialRepository farmerCredentialRepository;
	private final CoordinatesRepository coordinatesRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;
	//private final JwtService jwtService;
	private final RevokedTokenService revokedTokenService;
	
	@Override
	@Transactional
	public ResponseEntity<FarmerDto> registerFarmer(@Valid FarmerRegisterDto farmerRegisterDto) {
		checkEmail(farmerRegisterDto.getEmail());
		Farmer farmer = Farmer.of(farmerRegisterDto);
		farmerRepository.save(farmer);
		
		if (farmerRegisterDto.getCoordinates() != null) {
	        Coordinates coordinates = Coordinates.of(farmerRegisterDto.getCoordinates());
	       coordinates.setFarmer(farmer);
	       coordinatesRepository.save(coordinates);
	       farmer.setCoordinates(coordinates);
	    }
		
		FarmerCredential credential = FarmerCredential
				.builder()
				.createdAt(LocalDateTime.now())
				.passwordLastUpdated(LocalDateTime.now())
				.hashedPassword(passwordEncoder.encode(farmerRegisterDto.getPassword()))
				.farmer(farmer)
				.build();
		farmerCredentialRepository.save(credential);
		return ResponseEntity.status(HttpStatus.CREATED).body(FarmerDto.of(farmer));
	}
	
	private void checkEmail(String email) {
		if (farmerRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, FARMER_WITH_THIS_EMAIL_EXISTS);
		}
	}

	@Override
	public ResponseEntity<TokenResponseDto> loginFarmer(@Valid LoginRequestDto loginRequestDto) {
		String email = loginRequestDto.getEmail();
		
		Farmer farmer = getFarmerByEmail(email).getBody();
		
		farmer.getCredential().setLastLogin(LocalDateTime.now());
		
		TokenResponseDto tokens = authService.authenticate(email, loginRequestDto.getPassword());
		
		return ResponseEntity.ok(tokens);
		}

	

	@Override
	@Transactional
	public ResponseEntity<FarmerDto> updateFarmer(UUID id, @Valid FarmerEditDto farmerEditDto) {
		Farmer farmer = farmerRepository.findByid(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FARMER_NOT_FOUND));
		
		if (farmerEditDto.getFarmName() != null) {
	        farmer.setFarmName(farmerEditDto.getFarmName());
	        log.info("Service. FarmName updated");
	    }

	    if (farmerEditDto.getEmail() != null) {
	        farmer.setEmail(farmerEditDto.getEmail());
	        log.info("Service. Email updated");
	    }

	    if (farmerEditDto.getPhone() != null) {
	        farmer.setPhone(farmerEditDto.getPhone());
	        log.info("Service. Phone updated");
	    }
	    
	    if (farmerEditDto.getCoordinates() != null) {
	        Coordinates coordinates = farmerEditDto.getCoordinates();
	        log.info("coordinates updated successfully");
	        
	        coordinates.setFarmer(farmer); 
	        farmer.setCoordinates(coordinates);
	    }

	    farmerRepository.save(farmer); 
	    return ResponseEntity.ok(FarmerDto.of(farmer));
	}
	@Override
	public ResponseEntity<TokenResponseDto> updatePassword(UUID id, @Valid ChangePasswordRequestDto changePasswordDto) {
		Farmer farmer = getFarmerById(id).getBody();
		FarmerCredential credential = farmerCredentialRepository.findByFarmer(farmer);

		String oldPw = credential.getHashedPassword();

		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), oldPw)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OLD_PASSWORD_IS_NOT_CORECT);
		}
		credential.setHashedPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		credential.setPasswordLastUpdated(LocalDateTime.now());

		TokenResponseDto tokens = authService.authenticate(farmer.getEmail(), changePasswordDto.getNewPassword());

		return ResponseEntity.ok(tokens);
	}

	@Override
	public ResponseEntity<String> removeFarmerById(UUID id) {
		Farmer farmer = getFarmerById(id).getBody();
		farmerRepository.delete(farmer);
		return ResponseEntity.ok(FARMER_DELETED_SUCCESSFULLY);
	}

	@Override
	public ResponseEntity<String> logoutFarmer(UUID id, String token) {
		Farmer farmer = getFarmerById(id).getBody();
		FarmerCredential credential = farmerCredentialRepository.findByFarmer(farmer);
		token = token.substring(7);
		revokedTokenService.addToBlackList(token);
		credential.setRefreshToken("");
		return ResponseEntity.ok(LOGOUT_SUCCESS);
	}
	@Override
	public ResponseEntity<Farmer> getFarmerByEmail(String name) {
		Farmer farmer = farmerRepository.findByEmail(name).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FARMER_WITH_THIS_EMAIL_IS_NOT_EXISTS));
		return ResponseEntity.ok(farmer);
	}
	
	@Override
	public ResponseEntity<Farmer> getFarmerById(UUID id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FARMER_NOT_FOUND));
		return ResponseEntity.ok(farmer);
	}

}
