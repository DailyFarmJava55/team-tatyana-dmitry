package telran.dayli_farm.security.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "token_blacklist")
public class RevorkedToken {
	@Id
	UUID id;
 @Column(nullable = false, unique = true)
  private String token;
 
 @Column(nullable = false)
    private LocalDateTime expiresAt;

	public RevorkedToken(String token, LocalDateTime expiresAt) {
		this.token = token;
		this.expiresAt = expiresAt;
	}
}
