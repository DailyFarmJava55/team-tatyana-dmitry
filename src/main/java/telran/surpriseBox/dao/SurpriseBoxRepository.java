package telran.surpriseBox.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.surpriseBox.model.SurpriseBox;

public interface SurpriseBoxRepository extends JpaRepository<SurpriseBox, Long>{

}
