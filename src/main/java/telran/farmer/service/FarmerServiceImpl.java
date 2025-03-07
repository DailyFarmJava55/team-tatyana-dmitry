package telran.farmer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.dto.exceptions.UserNotFoundException;
import telran.auth.account.model.User;
import telran.farmer.dto.FarmerUpdateRequest;
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {
	private final UserRepository userRepository;
	@Override
	public List<User> getAllFarmers() {
		return userRepository.findAllFarmers();
	}

	@Override
	public User getFarmerById(UUID id) {
		return userRepository.findById(id)
				  .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

	@Override
	public User updateFarmer(UUID id, FarmerUpdateRequest request) {
		 User farmer = getFarmerById(id);
	        if (request.getEmail() != null) farmer.setEmail(request.getEmail());
	        return userRepository.save(farmer);
	}

	@Override
	public void deleteFarmer(UUID id) {
		userRepository.deleteById(id);

	}

	@Override
	public User getFarmerByEmail(String email) {
		return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

	@Override
	public User getFarmerByFarmName(String farmName) {
		return userRepository.findByFarmName(farmName)
				  .orElseThrow(() -> new UserNotFoundException("Farmer not found"));
	}

}
