package telran.auth.security;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.accessExpirationSec:600}") // 10 minutes
    private int accessExpirationSec;

    @Value("${jwt.refreshExpirationSec:604800}") // 7 days
    private int refreshExpirationSec;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final SecretKey secretKey = Keys.secretKeyFor(signatureAlgorithm);

    public String generateAccessToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) 
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationSec * 1000)) 
                .signWith(secretKey, signatureAlgorithm) 
                .compact();
    }

    public String generateRefreshTokenFarmer(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", "FARMER")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationSec * 1000)) 
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }
    
    public String generateRefreshTokenUser(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", "USER")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationSec * 1000)) 
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Authentication parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            if (email == null) {
                throw new JwtException("Invalid token: email not found");
            }

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
    }

    public UUID extractId(String refreshToken) {
        Claims claims = extractAllClaims(refreshToken);
        
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
	
	private Claims extractAllClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(secretKey)
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}
}
