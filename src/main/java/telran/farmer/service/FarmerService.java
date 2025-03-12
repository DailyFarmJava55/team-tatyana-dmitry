package telran.farmer.service;

import java.util.List;
import java.util.UUID;

import telran.auth.account.model.Farmer;
import telran.farmer.dto.FarmerUpdateRequest;

public interface FarmerService {

	List<Farmer> getAllFarmers();

	Farmer getFarmerById(UUID id);

	Farmer updateFarmer(UUID id, FarmerUpdateRequest request);

	void deleteFarmer(UUID id);

	Farmer getFarmerByEmail(String email);

	Farmer getFarmerByFarmName(String farmName);

}
