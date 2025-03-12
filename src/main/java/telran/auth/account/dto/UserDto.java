package telran.auth.account.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import telran.auth.account.model.Location;
import telran.auth.account.validation.ValidPassword;

@Data
@AllArgsConstructor
public class UserDto {
	private UUID id;
	@Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
	
	@ValidPassword
    private String password;
	
	@NotBlank(message = "Language is required")
    private String language;
	
	@NotBlank(message = "Timezone is required")
    private String timezone;
	
    private Location location;
}
