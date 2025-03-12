package telran.surpriseBox.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.surpriseBox.model.SurpriseBox;

public interface SurpriseBoxRepository extends JpaRepository<SurpriseBox, UUID>{

	boolean existsById(UUID id);

}
