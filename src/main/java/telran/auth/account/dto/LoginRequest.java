package telran.auth.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
