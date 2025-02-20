package telran.dailyFarm.accounting.service;

import org.springframework.stereotype.Service;

import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.farmer.LocationDto;
@Service
public interface GeolocationService {

	LocationDto getCoordinates(FarmerDto farmerDto);

}
