package telran.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.goods.model.Farmer;

public interface  FarmerRepository extends JpaRepository<Farmer, Long> {

}
