package telran.dayli_farm.surprise_bag.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Repository
public interface SurpriseBagRepository extends JpaRepository<SurpriseBag, UUID> {

	@Query("SELECT sb FROM SurpriseBag sb WHERE sb.id = :boxId AND sb.farmer.id = :farmerId")
	Optional<SurpriseBag> findByIdAndFarmerId(UUID boxId, UUID farmerId);

	@Query("SELECT sb FROM SurpriseBag sb")
	List<SurprisebagResponseDto> findAllSurpriseBags();

}
