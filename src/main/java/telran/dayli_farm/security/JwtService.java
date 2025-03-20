package telran.dayli_farm.security;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
	@Value("${jwt.accessExpirationSec:600}") // 10 minutes
	private int accessExpirationSec;

	@Value("${jwt.refreshExpirationSec:604800}") // 7 days
	private int refreshExpirationSec;

	@Value("${jwt.secret}") // Secret key from application.properties
	private String secretKey;

	private Key getSigningKey() {
		log.debug("JwtService. Decoding secretKey for signing...");
		Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		return key;
	}

	public String generateAccessToken(String uuid, String email) {
		String token = Jwts.builder().setSubject(uuid).claim("email", email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accessExpirationSec * 1000))
				.signWith(getSigningKey()).compact();

		log.debug("JwtService. Generated access token: {}", token);
		return token;
	}

	public String generateRefreshToken(String uuid, String email) {
		String token = Jwts.builder().setSubject(uuid).claim("email", email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationSec * 1000))
				.signWith(getSigningKey()).compact();

		log.debug("JwtService. Generated refresh token: {}", token);
		return token;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}

	public String extractEmail(String token) {
		try {
			Claims claims = extractAllClaims(token);
			return claims.get("email", String.class);
		} catch (JwtException e) {
			log.error("Failed to extract email from token: {}", e.getMessage());
			throw e;
		}
	}

	public UUID extractUserId(String token) {
		try {
			String subject = extractAllClaims(token).getSubject();
			return UUID.fromString(subject);
		} catch (JwtException e) {
			log.error("Failed to extract userId from token: {}", e.getMessage());
			throw e;
		}
	}

	public Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (JwtException e) {
			log.error("Failed to extract claims from token: {}", e.getMessage());
			throw e;
		}
	}

	public Date getExpirationDate(String token) {
		return extractAllClaims(token).getExpiration();
	}
}
