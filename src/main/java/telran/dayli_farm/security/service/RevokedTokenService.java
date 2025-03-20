package telran.dayli_farm.security.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.dayli_farm.security.dao.RevorkedTokenRepository;
import telran.dayli_farm.security.entity.RevorkedToken;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
	
	private final RevorkedTokenRepository revorkedTokenRepository;

	public void addToRevorkedList(String token) {
		if (!revorkedTokenRepository.existsByToken(token)){
			revorkedTokenRepository.save(new RevorkedToken(token, LocalDateTime.now().plusHours(2)));
		}
	}

	public boolean isRevorkedToken(String token) {
		return revorkedTokenRepository.existsByToken(token);
	}

}
