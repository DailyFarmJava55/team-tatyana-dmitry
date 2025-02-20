package telran.dailyFarm.accounting.model.farmer;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dailyFarm.accounting.model.Role;

@Entity
@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farmer  {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
	private Long id;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message = "Password cannot be null or blank")
	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;
	
	private String farmName;
	@Embedded
	private Address address;
	@Embedded
	private Location location;

}
