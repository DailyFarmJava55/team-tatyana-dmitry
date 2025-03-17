package telran.auth.security.jwt;

import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Claims;

public interface JwtService {

	String generateAccessToken(String email, String role);
	
	String generateRefreshToken(String email);
	
	boolean validateToken(String token);
	
	String extractEmail(String token);
	
	String extractRole(String token);
	
	UUID extractId(String token);
	
	Claims extractAllClaims(String token);
	
	Date getExpirationDate(String token);
}
