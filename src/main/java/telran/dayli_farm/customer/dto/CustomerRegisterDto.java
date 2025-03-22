package telran.dayli_farm.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.api.dto.BaseUserDto;
import telran.dayli_farm.api.dto.CoordinatesDto;

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
