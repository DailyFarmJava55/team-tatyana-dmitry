package telran.dayli_farm.surprise_bag.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Repository
public interface SurpriseBagRepository extends JpaRepository<SurpriseBag, UUID> {

	 @Query("SELECT sb FROM SurpriseBag sb JOIN FETCH sb.farmer WHERE sb.id = :boxId AND sb.farmer.id = :farmerId")
	    Optional<SurpriseBag> findByIdAndFarmerId(@Param("boxId") UUID boxId, @Param("farmerId") UUID farmerId);

	 @Query("""
			    SELECT sb FROM SurpriseBag sb
			    JOIN FETCH sb.farmer
			    LEFT JOIN FETCH sb.category
			    LEFT JOIN FETCH sb.size
			""")
	    List<SurpriseBag> findAllSurpriseBags();
	
	 @Query("""
	            SELECT sb FROM SurpriseBag sb
	            LEFT JOIN FETCH sb.category
	            LEFT JOIN FETCH sb.size
	            WHERE sb.id = :bagId
	           """)
	    Optional<SurpriseBag> findByIdWithDetails(@Param("bagId") UUID bagId);

}
