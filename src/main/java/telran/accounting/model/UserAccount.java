package telran.accounting.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    private UUID id;
	
	String email;

	@Id
	String password;
	String farmName; //only for Farmers
	Location location;
	LocalDateTime registrationDate;
	LocalDateTime lastEditDate;
	LocalDateTime lastAccessDate;
	
	
	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	Set<Role> role;
}
