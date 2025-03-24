package telran.dayli_farm.surprise_bag.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import telran.dayli_farm.surprise_bag.dto.SurprisebagDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagEditDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;

@Service
public interface ISbService {

	ResponseEntity<SurprisebagResponseDto> addSurpriseBag(UUID id, @Valid SurprisebagDto surpriseBagDto);

	ResponseEntity<List<SurprisebagResponseDto>> getAvailableSurpriseBagForFarmer(UUID id);

	ResponseEntity<List<SurprisebagResponseDto>> getAllSurpriseBags();

	ResponseEntity<SurprisebagResponseDto> updateSurpriseBag(UUID farmerId, UUID bagId,
			@Valid SurprisebagEditDto surpriseBagEditDto);

	void deleteSurpriseBag(UUID bagId);

}
