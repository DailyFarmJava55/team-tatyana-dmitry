package telran.accounting.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;


@Getter
@AllArgsConstructor 
@NoArgsConstructor
public class UserDto {
	String email;
	@Singular
	Set<String> roles;
}
