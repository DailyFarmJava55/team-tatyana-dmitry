package telran.dailyFarm.accounting.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDto {
	private Long id;
	private String username;
	private String email;
	private String role;
	private String farmName;
	
	private AddressDto  address;
	private LocationDto location;

}
