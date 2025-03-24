package telran.dayli_farm.surprise_bag.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.surprise_bag.dao.SurpriseBagRepository;
import telran.dayli_farm.surprise_bag.dto.SurprisebagDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class SbServiceImpl implements ISbService {
	
	private final FarmerRepository farmerRepository;
	private final SurpriseBagRepository sbRepository;
	
	@Override
	@Transactional
	public ResponseEntity<Void> addSurpriseBag(UUID farmerId, @Valid SurprisebagDto surpriseBagDto) {
		log.info("Creating new SurpriseBox for Farmer ID: {}", farmerId);
		//Farmer farmer = 
		return null;
	}

	@Override
	public ResponseEntity<List<SurprisebagResponseDto>> getAvailableSurpriseBagForFarmer(UUID farmerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<SurprisebagResponseDto>> getAllSurpriseBags() {
		// TODO Auto-generated method stub
		return null;
	}

}
