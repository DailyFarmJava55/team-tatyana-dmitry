package telran.surpriseBox.service.farmer;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import telran.surpriseBox.dto.SurpriseBoxDto;
import telran.surpriseBox.model.SurpriseBox;

public interface FarmerSurpriseBoxService {
	SurpriseBox createSurpriseBox(UUID farmerId, @Valid SurpriseBoxDto dto);

	SurpriseBox updateSurpriseBox(UUID farmerId, UUID boxId, SurpriseBoxDto dto);

	void deleteSurpriseBox(UUID farmerId, UUID boxId);

	List<SurpriseBox> getFarmerBoxes(UUID farmerId);

	List<SurpriseBox> getFarmerOrders(UUID farmerId);

	

}
