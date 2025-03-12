package telran.auth.account.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.surpriseBox.model.SurpriseBox;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String language;

	private String timezone;

	@Embedded
	private Location location;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SurpriseBox> purchasedBoxes;

	@CreationTimestamp
	private ZonedDateTime registeredAt;
	private ZonedDateTime lastLoginAt;

	public User(String email, String password, String language, Location location) {
		this.email = email;
		this.password = password;
		this.language = language;
		this.location = location;
	}

	public static boolean checkPassword(String rawPassword, String hashedPassword) {
		return BCrypt.checkpw(rawPassword, hashedPassword);
	}
}
