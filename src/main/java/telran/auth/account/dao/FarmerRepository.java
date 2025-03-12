package telran.auth.account.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.auth.account.model.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {
	Optional<Farmer> findByEmail(String email);

	List<Farmer> findAll();

	Optional<Farmer> findByFarmName(String farmName);

	boolean existsByEmail(String email);

	Farmer getFarmerById(UUID id);
}
