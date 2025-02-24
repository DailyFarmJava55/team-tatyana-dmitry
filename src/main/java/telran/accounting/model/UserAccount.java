package telran.accounting.model;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@IdClass(UserAccountId.class)
public class UserAccount {
	@Id
	String email;

	@Id
	String password;
	String farmName; //only for Farmers
	Location location;
	@ElementCollection(targetClass = Role.class)
	@Enumerated(EnumType.STRING)
	Set<Role> role;
}
