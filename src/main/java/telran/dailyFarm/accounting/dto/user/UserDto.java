package telran.dailyFarm.accounting.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
	private Long id;
	@NotEmpty(message = "Name must be filled")
	private String username;

	@Email(message = "Invalid email address")
	@NotEmpty(message = "Email should be filled")
	private String email;

	private String role;
}
