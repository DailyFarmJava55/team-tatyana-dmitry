package telran.farmer.service;

import java.util.List;
import java.util.UUID;

import telran.auth.account.model.User;
import telran.farmer.dto.FarmerUpdateRequest;

public interface FarmerService {

	List<User> getAllFarmers();

	User getFarmerById(UUID id);

	User updateFarmer(UUID id, FarmerUpdateRequest request);

	void deleteFarmer(UUID id);

	User getFarmerByEmail(String email);

	User getFarmerByFarmName(String farmName);

}
