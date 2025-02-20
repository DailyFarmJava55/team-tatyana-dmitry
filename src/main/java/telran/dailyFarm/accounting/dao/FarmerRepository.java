package telran.dailyFarm.accounting.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.dailyFarm.accounting.model.User;
import telran.dailyFarm.accounting.model.farmer.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
	Optional<Farmer> findByEmail(String email);
	Optional<Farmer> findByUsername(String username);
	User findFarmerByUsername(String username);
	
	
	List<Farmer> findFarmersByAddressCity(String city);
	
	@Query("SELECT f FROM Farmer f WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(f.location.latitude)) * " +
            "cos(radians(f.location.longitude) - radians(:longitude)) + sin(radians(:latitude)) * " +
            "sin(radians(f.location.latitude)))) <= :radius")
	List<Farmer> findNearbyFarmers(double latitude, double longitude, double radius);
	
	Optional<Farmer> findFarmerById(Long id);
	
	@Query("SELECT f FROM Farmer f")
	List<Farmer> findAllFarmers();

}
