package telran.dayli_farm.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRegisterDto extends BaseUserDto {
//TODO  validation
	private String city;
	private CoordinatesDto coordinates;
	String firstName;
	String lastName;

}
