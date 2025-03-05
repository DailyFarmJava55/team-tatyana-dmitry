package telran.auth.account.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import telran.auth.account.model.Location;

@Data
@AllArgsConstructor
public class FarmerDto {
	private UUID id;
	@Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
	
	@NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
	
	 @NotBlank(message = "Farm name is required")
    private String farmName;
	 
	 @NotBlank(message = "Language is required")
    private String language;
	 
	 @NotBlank(message = "Timezone is required")
    private String timezone;
	 
    private Location location;
   
}
