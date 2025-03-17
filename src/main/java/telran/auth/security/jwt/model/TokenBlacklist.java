package telran.auth.security.jwt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
@Getter
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
	 @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	  private String id;
	 
	 @Column(nullable = false, unique = true)
	  private String token;
	 
	 @Column(nullable = false)
	    private LocalDateTime expiresAt;

		public TokenBlacklist(String token, LocalDateTime expiresAt) {
			this.token = token;
			this.expiresAt = expiresAt;
		}
}
