package telran.dayli_farm.api.dto;

import static telran.dayli_farm.api.message.ErrorMessages.EMAIL_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.LAST_NAME_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.NAME_IS_NOT_VALID;
import static telran.dayli_farm.api.message.ErrorMessages.PHONE_NUMBER_IS_NOT_VALID;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerEditDto {

	@Pattern(regexp = "[A-Z][a-z]{1,20}([-\\s][A-Z][a-z]{1,20})*", message = NAME_IS_NOT_VALID)
	private String firstName;
	
	@Pattern(regexp = "[A-Z][a-z]{1,20}([-\\s][A-Z][a-z]{1,20})*", message = LAST_NAME_IS_NOT_VALID)
	private String lastName;
	
	 @Pattern(regexp = "\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}", message = PHONE_NUMBER_IS_NOT_VALID)
	private String phone;
	 
	 @Email(message = EMAIL_IS_NOT_VALID)
	private String email;
	 
	private String city;
}
