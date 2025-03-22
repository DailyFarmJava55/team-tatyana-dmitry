package telran.dayli_farm.security.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.dayli_farm.security.JwtService;
import telran.dayli_farm.security.dao.RevorkedTokenRepository;
import telran.dayli_farm.security.entity.RevorkedToken;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
	@Autowired
	JwtService jwtService;
	private final RevorkedTokenRepository revorkedTokenRepository;

	public void addToBlackList(String token) {
		if (!revorkedTokenRepository.existsByToken(token)) {
			long expirationTime = jwtService.extractExpiration(token).getTime() - new Date().getTime();
			revorkedTokenRepository.save(new RevorkedToken(token, expirationTime));
		}
	}

	public boolean isRevorkedToken(String token) {
		return revorkedTokenRepository.existsByToken(token);
	}

}
