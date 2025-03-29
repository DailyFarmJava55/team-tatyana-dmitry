package telran.dayli_farm.surprise_bag.service;

import static telran.dayli_farm.api.message.ErrorMessages.FARMER_NOT_FOUND;
import static telran.dayli_farm.api.message.ErrorMessages.SURPRISE_BAG_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.surprise_bag.dao.SurpriseBagRepository;
import telran.dayli_farm.surprise_bag.dto.SurprisebagDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagEditDto;
import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Service
@Slf4j
@RequiredArgsConstructor
public class SbServiceImpl implements ISbService {

	private final FarmerRepository farmerRepository;
	private final SurpriseBagRepository sbRepository;

	@Override
	@Transactional
	public ResponseEntity<SurprisebagResponseDto> addSurpriseBag(UUID farmerId, @Valid SurprisebagDto surpriseBagDto) {
		log.info("Creating new SurpriseBox for Farmer ID: {}", farmerId);
		Farmer farmer = farmerRepository.findById(farmerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FARMER_NOT_FOUND));

		SurpriseBag sb = SurpriseBag.builder().availableCount(surpriseBagDto.getAvailableCount())
				.description(surpriseBagDto.getDescription()).price(surpriseBagDto.getPrice()).farmer(farmer)
				.category(surpriseBagDto.getCategories()).size(surpriseBagDto.getSize())
				.pickupTimeStart(surpriseBagDto.getPickupTimeStart()).pickupTimeEnd(surpriseBagDto.getPickupTimeEnd())
				.isAvailable(surpriseBagDto.getAvailableCount() > 0).build();

		sbRepository.save(sb);
		log.info("SurpriseBox created with ID: {}", sb.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(SurprisebagResponseDto.of(sb));
	}

	@Override
	public ResponseEntity<List<SurprisebagResponseDto>> getAvailableSurpriseBagForFarmer(UUID farmerId) {
		log.info("Fetching available Surprise Bags for Farmer ID: {}", farmerId);

		Farmer farmer = farmerRepository.findById(farmerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FARMER_NOT_FOUND));

		List<SurprisebagResponseDto> sbList = farmer.getSurpriseBags().stream().map(SurpriseBag::buildFromEntity)
				.toList();

		return ResponseEntity.ok(sbList);
	}

	@Override
	public ResponseEntity<List<SurprisebagResponseDto>> getAllSurpriseBags() {
		List<SurpriseBag> surpriseBags = sbRepository.findAllSurpriseBags();
		 List<SurprisebagResponseDto> response = surpriseBags.stream()
		            .map(SurprisebagResponseDto::of)
		            .toList();
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<SurprisebagResponseDto> updateSurpriseBag(UUID farmerId, UUID bagId,
			@Valid SurprisebagEditDto surpriseBagEditDto) {
		
		log.info("Updating SurpriseBag with ID: {} for Farmer ID: {}", bagId, farmerId);
		SurpriseBag sb = sbRepository.findByIdAndFarmerId(bagId, farmerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
						"You are not allowed to update this Surprise Bag"));

		if (surpriseBagEditDto.getAvailableCount() != 0) {
	        sb.setAvailableCount(surpriseBagEditDto.getAvailableCount());
	        sb.setAvailable(surpriseBagEditDto.getAvailableCount() > 0);
	    }
	    if (surpriseBagEditDto.getDescription() != null) {
	        sb.setDescription(surpriseBagEditDto.getDescription());
	    }
	    if (surpriseBagEditDto.getPrice() != 0) {
	        sb.setPrice(surpriseBagEditDto.getPrice());
	    }
	    if (surpriseBagEditDto.getCategories() != null) {
	        sb.setCategory(surpriseBagEditDto.getCategories());
	    }
	    if (surpriseBagEditDto.getSize() != null) {
	        sb.setSize(surpriseBagEditDto.getSize());
	    }
	    if (surpriseBagEditDto.getPickupTimeStart() != null) {
	        sb.setPickupTimeStart(surpriseBagEditDto.getPickupTimeStart());
	    }
	    if (surpriseBagEditDto.getPickupTimeEnd() != null) {
	        sb.setPickupTimeEnd(surpriseBagEditDto.getPickupTimeEnd());
	    }


		sbRepository.save(sb);

		log.info("SurpriseBag with ID: {} updated successfully", sb.getId());

		return ResponseEntity.status(HttpStatus.OK).body(SurprisebagResponseDto.of(sb));
	}

	@Override
	public void deleteSurpriseBag(UUID bagId) {
		if (sbRepository.existsById(bagId)) {
			sbRepository.deleteById(bagId);
		}
		throw new IllegalArgumentException(SURPRISE_BAG_NOT_FOUND + bagId);
	}

	public boolean isFarmerOrAdmin(String username, UUID bagId) {
		Optional<SurpriseBag> bagOptional = sbRepository.findById(bagId);
		if (bagOptional.isEmpty()) {
			return false;
		}
		SurpriseBag surpriseBag = bagOptional.get();
		Farmer owner = surpriseBag.getFarmer();
		if (owner == null) {
			return false;
		}
		return owner.getEmail().equals(username);
	}

}
