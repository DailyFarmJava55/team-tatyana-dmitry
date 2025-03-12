package telran.auth.account.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

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

@Entity
@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor

public class Farmer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String farmName;
	private String language;

	private String timezone;

	@Embedded
	private Location Location;
	
	@OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private List<SurpriseBox> surpriseBoxes;
	
	@CreationTimestamp
	private ZonedDateTime registeredAt;
	private ZonedDateTime lastLoginAt;
	
	public Farmer(String email, String password, String farmName, String language, String timezone, Location location) {
		this.email = email;
		this.password = password;
		this.farmName = farmName;
		this.language = language;
		this.Location = location;
		this.timezone = timezone;
	}
}
