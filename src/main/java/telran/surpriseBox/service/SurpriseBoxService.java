package telran.surpriseBox.service;

import java.util.List;

import telran.surpriseBox.model.SurpriseBox;

public interface SurpriseBoxService {
    SurpriseBox createSurpriseBox(SurpriseBox surpriseBox);
    List<SurpriseBox> getAllSurpriseBoxes();
    SurpriseBox getSurpriseBoxById(Long id);
    SurpriseBox updateSurpriseBox(Long id, SurpriseBox surpriseBox);
    void deleteSurpriseBox(Long id);
}
