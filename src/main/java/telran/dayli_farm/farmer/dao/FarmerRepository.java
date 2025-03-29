package telran.dayli_farm.farmer.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.dayli_farm.farmer.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, UUID> {

	@Query("SELECT f FROM Farmer f WHERE f.email = :email")
	Optional<Farmer> findByEmail(String email);

	Optional<Farmer> findByid(UUID id);

	boolean existsByEmail(String email);

}
