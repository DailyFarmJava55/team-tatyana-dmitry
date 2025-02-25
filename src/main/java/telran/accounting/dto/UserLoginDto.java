package telran.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor 
@NoArgsConstructor
public class UserLoginDto {
	String email;
	String password;
}
