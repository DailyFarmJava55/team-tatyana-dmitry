package telran.auth.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.expirationTimeSec:60}")
	 private int expirationTimeSec;

	 private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	 SecretKey secretKey = Keys.secretKeyFor(signatureAlgorithm);

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
        		.setClaims(new HashMap<>())
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeSec * 1000))
                .claim("roles", AuthorityUtils.authorityListToSet(authentication.getAuthorities()))
                .signWith(secretKey,signatureAlgorithm) 
                .compact();
    }

	public Authentication parseToken(String token) throws JwtException {
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
		String email = claims.getSubject();
		List<?>roleList = claims.get("roles", List.class);
		if (email == null || roleList == null) {
			throw new JwtException("Insufficient claims are specified");
		}
		return new UsernamePasswordAuthenticationToken(
				email,
				null,
				AuthorityUtils.createAuthorityList(roleList.toArray(new String[0])));
	}
}
