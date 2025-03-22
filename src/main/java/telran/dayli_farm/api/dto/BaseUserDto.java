package telran.dayli_farm.api.dto;

import static telran.dayli_farm.api.message.ErrorMessages.EMAIL_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.PASSWORD_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.PASSWORD_IS_REQUIRED;
import static telran.dayli_farm.api.message.ErrorMessages.PHONE_IS_REQUIRED;
import static telran.dayli_farm.api.message.ErrorMessages.PHONE_NUMBER_IS_NOT_VALID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class BaseUserDto {
	@Pattern(regexp = "\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}", message = PHONE_NUMBER_IS_NOT_VALID)
	@NotBlank( message = PHONE_IS_REQUIRED)
	String phone;
	
	@NotBlank( message = EMAIL_IS_NOT_VALID)
	@Email( message = EMAIL_IS_NOT_VALID)
	String email;
	
	@Size(min = 8, message = PASSWORD_IS_NOT_VALID)
	@NotBlank( message = PASSWORD_IS_REQUIRED)
	String password;
}
