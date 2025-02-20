package telran.dailyFarm.accounting.dto.auth;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class LoginRequestDto {
	@NotBlank(message = "Email is required")
	private String email;
	@NotBlank(message = "Password is required")
    private String password;
    
}
