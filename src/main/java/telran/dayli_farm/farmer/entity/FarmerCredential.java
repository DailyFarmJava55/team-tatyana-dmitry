package telran.dayli_farm.farmer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "farmer_credentials")
public class FarmerCredential {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@OneToOne
	@JoinColumn(name = "farmer_id", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Farmer farmer;

	@Column(nullable = false)
	private String hashedPassword;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime lastLogin;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean isVerificated;

	@Column(nullable = false)
	private LocalDateTime passwordLastUpdated;

	private String refreshToken;

}
