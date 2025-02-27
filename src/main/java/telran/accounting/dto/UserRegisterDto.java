package telran.accounting.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRegisterDto {
	@NotNull
	String email;
	@NotNull
	String password;
	
}
