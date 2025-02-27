package telran.accounting.dto;

import lombok.Getter;

@Getter
public class FarmerRegisterDto {

	String email;
	String password;
	String farmName;
	LocationDto location;

}
