package telran.dayli_farm.security.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "token_blacklist")
public class RevorkedToken {
	@UuidGenerator
	@Id
	private
	UUID id;
 @Column(nullable = false, unique = true)
  private String token;
 
 @Column(nullable = false)
    private long expiresAt;

	public RevorkedToken(String token, long expirationTime) {
		this.token = token;
		this.expiresAt = expirationTime;
	}
}
