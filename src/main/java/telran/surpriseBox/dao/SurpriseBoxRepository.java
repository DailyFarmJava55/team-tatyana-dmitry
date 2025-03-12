package telran.surpriseBox.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.surpriseBox.model.SurpriseBox;

@Repository
public interface SurpriseBoxRepository extends JpaRepository<SurpriseBox, UUID> {

	boolean existsById(UUID id);

	List<SurpriseBox> findByFarmerId(UUID farmerId);

	List<SurpriseBox> findByFarmerIdAndCustomerIsNotNull(UUID farmerId);
	
	//Customer
	
	 List<SurpriseBox> findByCustomerId(UUID customerId);
	 
	 List<SurpriseBox> findByCustomerIsNull();

}
