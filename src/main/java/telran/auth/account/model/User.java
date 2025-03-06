package telran.auth.account.model;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
	    
	    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	    @Enumerated(EnumType.STRING)
	    private Set<Role> roles = new HashSet<>();
	    
	    @Embedded
	    private Location location;
	    
	    private String farmName;
	    
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
	    
	    public boolean addRole(String role) {
			return roles.add(Role.valueOf(role.toUpperCase()));
		}
		
		public boolean removeRole(String role) {
			return roles.remove(Role.valueOf(role.toUpperCase()));
		}
}
