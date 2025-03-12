package telran.surpriseBox.service;

import java.util.List;
import java.util.UUID;

import telran.surpriseBox.model.SurpriseBox;

public interface SurpriseBoxService {
	SurpriseBox createSurpriseBox(SurpriseBox surpriseBox);

	List<SurpriseBox> getAllSurpriseBoxes();

	SurpriseBox updateSurpriseBox(UUID id, SurpriseBox surpriseBox);

	SurpriseBox getSurpriseBoxById(UUID id);

	void deleteSurpriseBox(UUID id);
}
