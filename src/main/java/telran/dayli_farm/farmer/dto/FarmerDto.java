package telran.dayli_farm.farmer.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.farmer.entity.Farmer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FarmerDto {
	private UUID id;
	 private String email;
	 private String farmName;
	 private String phone;
	 private CoordinatesDto coordinates;
	
	 
	 public static FarmerDto of(Farmer farmer) {
			return FarmerDto.builder()
					.id(farmer.getId())
					.email(farmer.getEmail())
					.farmName(farmer.getFarmName())
					.phone(farmer.getPhone())
					 .coordinates(farmer.getCoordinates() != null ? CoordinatesDto.of(farmer.getCoordinates()) : null)
					.build();
		}
	

}
