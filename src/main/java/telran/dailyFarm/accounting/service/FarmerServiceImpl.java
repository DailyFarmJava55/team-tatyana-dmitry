package telran.dailyFarm.accounting.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dao.FarmerRepository;
import telran.dailyFarm.accounting.dto.exception.FarmerNotFoundException;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.farmer.LocationDto;
import telran.dailyFarm.accounting.dto.user.UserEditDto;
import telran.dailyFarm.accounting.model.farmer.Address;
import telran.dailyFarm.accounting.model.farmer.Farmer;
import telran.dailyFarm.accounting.model.farmer.Location;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

	private final FarmerRepository farmerRepository;
	private final GeolocationService geolocationService;
	private final UserService userService;
	private final ModelMapper modelMapper;

	@Override
	public Iterable<FarmerDto> getAllFarmers() {
		return farmerRepository.findAllFarmers().stream().map(farmer -> modelMapper.map(farmer, FarmerDto.class))
				.toList();
	}

	@Override
	public FarmerDto getFarmerById(Long id) {
		return mapToDto(findFarmerById(id));
	}

	@Transactional
	@Override
	public FarmerDto updateFarmer(Long id, FarmerDto dto) {
		Farmer farmer = findFarmerById(id);

		Optional.ofNullable(dto.getFarmName()).ifPresent(farmer::setFarmName);

		if (dto.getAddress() != null) {
			if (farmer.getAddress() == null) {
				farmer.setAddress(new Address());
			}
			Optional.ofNullable(dto.getAddress().getCountry()).ifPresent(farmer.getAddress()::setCountry);
			Optional.ofNullable(dto.getAddress().getCity()).ifPresent(farmer.getAddress()::setCity);
			Optional.ofNullable(dto.getAddress().getStreet()).ifPresent(farmer.getAddress()::setStreet);
			Optional.ofNullable(dto.getAddress().getHouseNumber()).ifPresent(farmer.getAddress()::setHouseNumber);
			Optional.ofNullable(dto.getAddress().getZipCode()).ifPresent(farmer.getAddress()::setZipCode);
		}
		if (dto.getAddress() != null) {
			LocationDto locationDto = geolocationService.getCoordinates(dto);
			if (locationDto != null) {
				farmer.setLocation(new Location(locationDto.getLatitude(), locationDto.getLongitude()));
			}
		}
		updateUsernameAndEmail(farmer, dto);

		return mapToDto(farmerRepository.save(farmer));
	}

	@Transactional
	private void updateUsernameAndEmail(Farmer farmer, FarmerDto dto) {
		if (dto.getUsername() != null || dto.getEmail() != null) {
			userService.updateUser(farmer.getId(), new UserEditDto(dto.getUsername(), dto.getEmail()));
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<FarmerDto> findFarmersByCity(String city) {
		return farmerRepository.findFarmersByAddressCity(city).stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public List<FarmerDto> findNearbyFarmers(double latitude, double longitude, double radius) {
		return farmerRepository.findNearbyFarmers(latitude, longitude, radius).stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}

	private Farmer findFarmerById(Long id) {
		return farmerRepository.findFarmerById(id).orElseThrow(FarmerNotFoundException::new);
	}

	private FarmerDto mapToDto(Farmer farmer) {
		return modelMapper.map(farmer, FarmerDto.class);
	}

}