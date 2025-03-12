package telran.surpriseBox.service.farmer;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.auth.account.dao.FarmerRepository;
import telran.auth.account.model.Farmer;
import telran.surpriseBox.dao.SurpriseBoxRepository;
import telran.surpriseBox.dto.SurpriseBoxDto;
import telran.surpriseBox.model.SurpriseBox;
import telran.utils.exceptions.FarmerNotFoundException;
import telran.utils.exceptions.SurpriseBoxNotFoundException;
import telran.utils.exceptions.UnauthorizedActionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerSurpriseBoxServiceImpl implements FarmerSurpriseBoxService {
	
	private final SurpriseBoxRepository surpriseBoxRepository;
    private final FarmerRepository farmerRepository;

    @Transactional
	@Override
	public SurpriseBox createSurpriseBox(UUID farmerId, @Valid SurpriseBoxDto surpriseBoxDto) {
    	 log.info("Creating new SurpriseBox for Farmer ID: {}", farmerId);
		 Farmer farmer = farmerRepository.findById(farmerId)
				 .orElseThrow(() -> new FarmerNotFoundException("Farmer with ID " + farmerId + " not found"));

		        SurpriseBox surpriseBox = new SurpriseBox();
		        surpriseBox.setName(surpriseBoxDto.getName());
		        surpriseBox.setDescription(surpriseBoxDto.getDescription());
		        surpriseBox.setPrice(surpriseBoxDto.getPrice());
		        surpriseBox.setFarmer(farmer);
		        surpriseBox.setBoxSize(surpriseBoxDto.getBoxSize());
		        surpriseBox.setQuantity(surpriseBoxDto.getQuantity());
		        surpriseBox.setCategories(surpriseBoxDto.getCategories());

		        SurpriseBox savedBox = surpriseBoxRepository.save(surpriseBox);
		        log.info("SurpriseBox created with ID: {}", savedBox.getId());
		        
		        return savedBox;
	}

    @Transactional
	@Override
	public SurpriseBox updateSurpriseBox(UUID farmerId, UUID boxId, SurpriseBoxDto surpriseBoxDto) {
    	log.info("Updating SurpriseBox ID: {} for Farmer ID: {}", boxId, farmerId);
		 SurpriseBox surpriseBox = surpriseBoxRepository.findById(boxId)
				 .orElseThrow(() -> new SurpriseBoxNotFoundException("SurpriseBox with ID " + boxId + " not found"));

		        if (!surpriseBox.getFarmer().getId().equals(farmerId)) {
		        	log.warn("Access denied: Farmer ID {} is not the owner of box ID {}", farmerId, boxId);
		        	 throw new UnauthorizedActionException("You are not the owner of this box!");
		        }

		        surpriseBox.setName(surpriseBoxDto.getName());
		        surpriseBox.setDescription(surpriseBoxDto.getDescription());
		        surpriseBox.setPrice(surpriseBoxDto.getPrice());
		        surpriseBox.setBoxSize(surpriseBoxDto.getBoxSize());
		        surpriseBox.setQuantity(surpriseBoxDto.getQuantity());
		        surpriseBox.setCategories(surpriseBoxDto.getCategories());

		        SurpriseBox updatedBox = surpriseBoxRepository.save(surpriseBox);
		        log.info("SurpriseBox updated: ID {}", updatedBox.getId());

		        return updatedBox;
	}

    @Transactional
	@Override
	public void deleteSurpriseBox(UUID farmerId, UUID boxId) {
    	  log.info("Deleting SurpriseBox ID: {} for Farmer ID: {}", boxId, farmerId);
    	  
		 SurpriseBox surpriseBox = surpriseBoxRepository.findById(boxId)
				 .orElseThrow(() -> new SurpriseBoxNotFoundException("SurpriseBox with ID " + boxId + " not found"));

		        if (!surpriseBox.getFarmer().getId().equals(farmerId)) {
		        	log.warn("Access denied: Farmer ID {} is not the owner of box ID {}", farmerId, boxId);
		        	 throw new UnauthorizedActionException("You are not the owner of this box!");
		        }

		        surpriseBoxRepository.delete(surpriseBox);
		        log.info("SurpriseBox deleted: ID {}", boxId);

	}

	@Override
	public List<SurpriseBox> getFarmerBoxes(UUID farmerId) {
		 log.info("Fetching all boxes for Farmer ID: {}", farmerId);
		 return surpriseBoxRepository.findByFarmerId(farmerId);
	}

	@Override
	public List<SurpriseBox> getFarmerOrders(UUID farmerId) {
		log.info("Fetching all sold boxes for Farmer ID: {}", farmerId);
		 return surpriseBoxRepository.findByFarmerIdAndCustomerIsNotNull(farmerId);
	}

	

}
