package telran.auth.security.jwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.auth.security.jwt.model.TokenBlacklist;
@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
	boolean existsByToken(String token);

}
