package telran.dayli_farm.farmer.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import telran.dayli_farm.api.dto.ChangePasswordRequestDto;
import telran.dayli_farm.api.dto.LoginRequestDto;
import telran.dayli_farm.api.dto.token.TokenResponseDto;
import telran.dayli_farm.farmer.dto.FarmerDto;
import telran.dayli_farm.farmer.dto.FarmerEditDto;
import telran.dayli_farm.farmer.dto.FarmerRegisterDto;
import telran.dayli_farm.farmer.entity.Farmer;

@Service
public interface IFarmerService {

	ResponseEntity<FarmerDto> registerFarmer(@Valid FarmerRegisterDto farmerRegisterDto);

	ResponseEntity<TokenResponseDto> loginFarmer(@Valid LoginRequestDto loginRequestDto);

	ResponseEntity<Farmer> getFarmerByEmail(String name);

	ResponseEntity<FarmerDto> updateFarmer(UUID id, @Valid FarmerEditDto farmerEditDto);

	ResponseEntity<TokenResponseDto> updatePassword(UUID id, @Valid ChangePasswordRequestDto changePasswordDto);

	ResponseEntity<String> removeFarmerById(UUID id);

	ResponseEntity<String> logoutFarmer(UUID id, String token);

	ResponseEntity<Farmer> getFarmerById(UUID id);

}
