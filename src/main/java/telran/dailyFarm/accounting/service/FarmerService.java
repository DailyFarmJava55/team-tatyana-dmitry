package telran.dailyFarm.accounting.service;

import org.springframework.stereotype.Service;

import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
@Service
public interface FarmerService {

	Iterable<FarmerDto> getAllFarmers();

	FarmerDto getFarmerById(Long id);

	FarmerDto updateFarmer(Long id, FarmerDto dto);

	Iterable<FarmerDto> findFarmersByCity(String city);

	Iterable<FarmerDto> findNearbyFarmers(double latitude, double longitude, double radius);

}
