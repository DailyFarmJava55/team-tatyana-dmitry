package telran.dayli_farm.farmer.dto;

import static telran.dayli_farm.api.message.ErrorMessages.PHONE_NUMBER_IS_NOT_VALID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.dayli_farm.farmer.entity.Coordinates;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FarmerEditDto {
	@Pattern(regexp = "\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}", message = PHONE_NUMBER_IS_NOT_VALID)
	private String phone;
	
	private String farmName;
	
	private String email;
	
	@Valid
	private Coordinates coordinates;
}
