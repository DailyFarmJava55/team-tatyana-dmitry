package telran.dayli_farm.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.api.dto.CustomerRegisterDto;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "customers")
public class Customer {

	@Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
	@Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    private String city;

//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//	List<Order> orders;
    
    
	   public Customer(UUID id) { 
	        this.id = id;
	    }


    public static Customer of(CustomerRegisterDto dto) {
        return Customer.builder()
            .email(dto.getEmail())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .phone(dto.getPhone())
            .city(dto.getCity())
            .build();
    }
}
