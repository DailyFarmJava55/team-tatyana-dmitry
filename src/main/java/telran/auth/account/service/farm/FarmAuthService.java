package telran.auth.account.service.farm;

import org.springframework.security.core.Authentication;

import telran.auth.account.dto.FarmerDto;
import telran.auth.account.model.User;

public interface FarmAuthService {

	String registerFarmer(FarmerDto farmerDto);
	
	String login(Authentication auth);

    void logout(String email);

	User findFarmerByEmail(String name);

	FarmerDto getFarmer(String name);
}
