package telran.dayli_farm.security.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import telran.dayli_farm.security.entity.RevorkedToken;

public interface RevorkedTokenRepository extends JpaRepository<RevorkedToken, UUID> {

	boolean existsByToken(String token);

}
