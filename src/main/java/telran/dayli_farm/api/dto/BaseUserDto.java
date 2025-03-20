package telran.dayli_farm.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class BaseUserDto {
	//TODO  validation
	String phone;
	String email;
	String password;
}
