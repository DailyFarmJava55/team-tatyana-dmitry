package telran.dayli_farm.farmer.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.farmer.dto.FarmerRegisterDto;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "farmers")
public class Farmer {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String farmName;

	@Column(nullable = false)
    private String phone;
	
	@OneToOne(mappedBy = "farmer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private Coordinates coordinates;
	
	@OneToOne(mappedBy = "farmer", cascade = jakarta.persistence.CascadeType.REMOVE, fetch = FetchType.LAZY)
    private FarmerCredential credential;
	
	@OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<SurpriseBag> surpriseBags;
	
	public Farmer(UUID id) { 
        this.id = id;
    }
	
	 public static Farmer of(@Valid FarmerRegisterDto dto) {
	        return Farmer.builder()
	            .email(dto.getEmail())
	            .farmName(dto.getFarmName())
	            .phone(dto.getPhone())
	            .build();
	    }
}
