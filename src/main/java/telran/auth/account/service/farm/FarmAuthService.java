package telran.auth.account.service.farm;

import java.util.UUID;

import telran.auth.account.dto.AuthRequestDto;
import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;

public interface FarmAuthService {

	AuthResponse registerFarmer(FarmerDto farmerDto);

	void logout(String email);

	FarmerDto getFarmer(String name);

	AuthResponse authenticateFarmer(AuthRequestDto request);

	AuthResponse refreshAccessToken(String refreshToken);
}
