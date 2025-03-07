package telran.surpriseBox.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.surpriseBox.dao.SurpriseBoxRepository;
import telran.surpriseBox.model.SurpriseBox;

@RequiredArgsConstructor
@Service
public class SurpriseBoxServiceImpl implements SurpriseBoxService {

    final SurpriseBoxRepository surpriseBoxRepository;

    @Override
    public SurpriseBox createSurpriseBox(SurpriseBox surpriseBox) {
        return surpriseBoxRepository.save(surpriseBox);
    }

    @Override
    public List<SurpriseBox> getAllSurpriseBoxes() {
        return surpriseBoxRepository.findAll();
    }

    @Override
    public SurpriseBox getSurpriseBoxById(Long id) {
        Optional<SurpriseBox> surpriseBox = surpriseBoxRepository.findById(id);
        return surpriseBox.orElse(null);
    }

    @Override
    public SurpriseBox updateSurpriseBox(Long id, SurpriseBox surpriseBox) {
        if (surpriseBoxRepository.existsById(id)) {
            surpriseBox.setId(id);
            return surpriseBoxRepository.save(surpriseBox);
        }
        return null;
    }

    @Override
    public void deleteSurpriseBox(Long id) {
        surpriseBoxRepository.deleteById(id);
    }
}