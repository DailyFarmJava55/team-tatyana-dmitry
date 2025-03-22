package telran.dayli_farm.api.dto;

import static telran.dayli_farm.api.message.ErrorMessages.OLD_NEW_PASSWORD_REQUARED;
import static telran.dayli_farm.api.message.ErrorMessages.PASSWORD_IS_NOT_VALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChangePasswordRequestDto {
	@NotBlank(message = OLD_NEW_PASSWORD_REQUARED)
	@Size(min = 8, message = PASSWORD_IS_NOT_VALID)
	String oldPassword;
	
	
	@Size(min = 8, message = PASSWORD_IS_NOT_VALID)
	@NotBlank(message = OLD_NEW_PASSWORD_REQUARED)
	String newPassword;
}
