package telran.dailyFarm.accounting.model.farmer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
	 private String country;
	    private String city;
	    private String street;
	    private String houseNumber;
	    private String zipCode;
}
