package telran.auth.security.jwt;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.auth.security.jwt.dao.TokenBlacklistRepository;
import telran.auth.security.jwt.model.TokenBlacklist;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
	 private final TokenBlacklistRepository tokenBlacklistRepository;

	 @Transactional
	    public void revokeToken(String token) {
	        if (!tokenBlacklistRepository.existsByToken(token)) {
	            TokenBlacklist revokedToken = new TokenBlacklist(token, LocalDateTime.now().plusHours(2)); 
	            tokenBlacklistRepository.save(revokedToken);
	        }
	    }

	    public boolean isTokenRevoked(String token) {
	        return tokenBlacklistRepository.existsByToken(token);
	    }
}
