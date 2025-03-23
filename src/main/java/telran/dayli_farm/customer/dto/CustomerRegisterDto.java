package telran.dayli_farm.customer.dto;

import static telran.dayli_farm.api.message.ErrorMessages.LAST_NAME_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.NAME_IS_NOT_VALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.api.dto.BaseUserDto;
import telran.dayli_farm.farmer.dto.CoordinatesDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRegisterDto extends BaseUserDto {
	
	private String city;
	private CoordinatesDto coordinates;
	
	@NotBlank
	@Pattern(regexp = "[A-Z][a-z]{1,20}([-\\s][A-Z][a-z]{1,20})*", message = NAME_IS_NOT_VALID)
	String firstName;
	
	@NotBlank
	@Pattern(regexp = "[A-Z][a-z]{1,20}([-\\s][A-Z][a-z]{1,20})*", message = LAST_NAME_IS_NOT_VALID)
	String lastName;

}
