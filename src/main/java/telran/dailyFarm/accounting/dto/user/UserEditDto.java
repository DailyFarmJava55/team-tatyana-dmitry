package telran.dailyFarm.accounting.dto.user;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEditDto {
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@NotEmpty(message = "Name must be filled")
	private String username;
	
	@Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
	private String email;
}
