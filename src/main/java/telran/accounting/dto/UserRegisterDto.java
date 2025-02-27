package telran.accounting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRegisterDto {
	@NotNull
	String email;
	@NotNull
	String password;

}
