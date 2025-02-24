package telran.auth.account.service.farm;

import telran.auth.account.dto.AuthResponse;
import telran.auth.account.dto.FarmerDto;
import telran.auth.account.dto.AuthRequestDto;

public interface FarmService {

	void registerFarmer(FarmerDto farmerDto);
	
    AuthResponse login(AuthRequestDto request);

    void logout(String email);
}
