package telran.accounting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor 
@NoArgsConstructor
public class UserLoginDto {
	@NotNull
	String email;
	@NotNull
	String password;
}
