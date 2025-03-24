package telran.dayli_farm.surprise_bag.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.surprise_bag.model.SurpriseBag;
@Repository
public interface SurpriseBagRepository extends JpaRepository<SurpriseBag, UUID> {

}
