package telran.dailyFarm.accounting.dto.farmer;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dailyFarm.accounting.model.farmer.Address;
import telran.dailyFarm.accounting.model.farmer.Location;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerRegisterDto {
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	private String username;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;

	@NotBlank(message = "Role is required")
	@Pattern(regexp = "^(USER|FARMER|ADMIN)$", message = "Role must be USER, FARMER, or ADMIN")
	private String role;

	@Size(min = 3, max = 50, message = "Farm name must be between 3 and 50 characters")
	private String farmName;

	@Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
	private String country;

	@Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
	private String city;

	@Size(min = 2, max = 50, message = "Street must be between 2 and 50 characters")
	private String street;

	@Pattern(regexp = "^[0-9A-Za-z -]+$", message = "House number contains invalid characters")
	private String houseNumber;
	
	@Pattern(regexp = "^\\d{4,10}$", message = "Zip code must be 4-10 digits long")
	private String zipCode;

	@DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
	@DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
	private Double latitude;

	@DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
	@DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
	private Double longitude;
	
	
	public Address toAddress() {
	    return new Address(country, city, street, houseNumber, zipCode);
	}

	public Location toLocation() {
	    return new Location(latitude, longitude);
	}

}
