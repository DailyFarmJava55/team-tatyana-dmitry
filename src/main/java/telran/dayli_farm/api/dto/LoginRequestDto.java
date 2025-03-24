package telran.dayli_farm.api.dto;

import static telran.dayli_farm.api.message.ErrorMessages.EMAIL_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.PASSWORD_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.PASSWORD_IS_REQUIRED;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequestDto {

	@NotBlank(message = EMAIL_IS_NOT_VALID)
	@Email(message = EMAIL_IS_NOT_VALID)
	private String email;

	@Size(min = 8, message = PASSWORD_IS_NOT_VALID)
	@NotBlank(message = PASSWORD_IS_REQUIRED)
	private String password;
}
