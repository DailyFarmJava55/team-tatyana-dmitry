package telran.dayli_farm.farmer.dto;

import static telran.dayli_farm.api.message.ErrorMessages.FARM_NAME_IS_REQUIRED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.dayli_farm.api.dto.BaseUserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class FarmerRegisterDto extends BaseUserDto {
	
	@NotBlank(message = FARM_NAME_IS_REQUIRED)
	String farmName;
	
	@Valid
	CoordinatesDto coordinates;

}
