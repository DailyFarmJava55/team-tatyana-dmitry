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

	List<SurpriseBox> findByFarmerIdAndCustomersIsNotEmpty(UUID farmerId);
	
	//Customer
	
	List<SurpriseBox> findByCustomers_Id(UUID customerId);
	 
	 List<SurpriseBox> findByCustomersIsEmpty();

}
