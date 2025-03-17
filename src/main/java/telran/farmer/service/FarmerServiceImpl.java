package telran.farmer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.FarmerRepository;
import telran.auth.account.model.Farmer;
import telran.farmer.dto.FarmerUpdateRequest;
import telran.utils.exceptions.UserNotFoundException;
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {
	private final FarmerRepository farmerRepository;
	@Override
	public List<Farmer> getAllFarmers() {
		return farmerRepository.findAll();
	}

	@Override
	public Farmer getFarmerById(UUID id) {
		return farmerRepository.findById(id)
				  .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

	@Override
	public Farmer updateFarmer(UUID id, FarmerUpdateRequest request) {
		 Farmer farmer = getFarmerById(id);
	        if (request.getEmail() != null) farmer.setEmail(request.getEmail());
	        return farmerRepository.save(farmer);
	}

	@Override
	public void deleteFarmer(UUID id) {
		farmerRepository.deleteById(id);

	}

	@Override
	public Farmer getFarmerByEmail(String email) {
		return farmerRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

	@Override
	public Farmer getFarmerByFarmName(String farmName) {
		return farmerRepository.findByFarmName(farmName)
				  .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

	

}
