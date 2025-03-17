package telran.auth.security.jwt.farmer;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import telran.auth.security.jwt.JwtService;

@Service("farmJwtService")
public class FarmJwtServiceImpl implements JwtService {

	@Value("${jwt.accessExpirationSec:600}") // 10 minutes
	private int accessExpirationSec;

	@Value("${jwt.refreshExpirationSec:604800}") // 7 days
	private int refreshExpirationSec;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private final SecretKey secretKey = Keys.secretKeyFor(signatureAlgorithm);

	@Override
	public String generateAccessToken(String email, String role) {
		return Jwts.builder().setSubject(email).claim("role", role).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accessExpirationSec * 1000))
				.signWith(secretKey, signatureAlgorithm).compact();
	}

	@Override
	public String generateRefreshToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationSec * 1000))
				.signWith(secretKey, signatureAlgorithm).compact();
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	@Override
	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	@Override
	public String extractRole(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role",
				String.class);
	}

	@Override
	public UUID extractId(String token) {
		Claims claims = extractAllClaims(token);

		Object idObject = claims.get("id");

		if (idObject == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID not found in token");
		}

		String idString = idObject.toString();

		try {
			return UUID.fromString(idString);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UUID format in token");
		}
	}

	@Override
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	@Override
	public Date getExpirationDate(String token) {
		return extractAllClaims(token).getExpiration();
	}

}
