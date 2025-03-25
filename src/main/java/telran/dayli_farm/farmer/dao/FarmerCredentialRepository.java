package telran.dayli_farm.farmer.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;

public interface FarmerCredentialRepository extends JpaRepository<FarmerCredential, UUID> {

	FarmerCredential findByFarmer(Farmer farmer);
	
	 @Query("SELECT f FROM FarmerCredential f JOIN f.farmer fr WHERE fr.email = :email")
	    Optional<FarmerCredential> findByFarmerEmail(@Param("email") String email);



}
