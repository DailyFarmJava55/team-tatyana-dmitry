package telran.accounting.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    UUID id;
	
	String email;
	String password;
	String farmName; //only for Farmers
	Location location;
	LocalDateTime registrationDate;
	LocalDateTime lastEditDate;
	LocalDateTime lastAccessDate;
	
	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	@Singular
	Set<Role> roles;
	
	public UserAccount() {
		roles = new HashSet<>();
	}

	
	
	public boolean addRole(String role) {
		return roles.add(Role.valueOf(role.toUpperCase()));
	}
	
	public boolean removeRole(String role) {
		return roles.remove(Role.valueOf(role.toUpperCase()));
	}
}
