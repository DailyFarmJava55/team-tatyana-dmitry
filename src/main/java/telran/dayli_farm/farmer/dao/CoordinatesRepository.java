package telran.dayli_farm.farmer.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.dayli_farm.farmer.entity.Coordinates;
import telran.dayli_farm.farmer.entity.Farmer;

public interface CoordinatesRepository extends JpaRepository<Coordinates, UUID> {
	
	Coordinates findByFarmer(Farmer farmer);
}
