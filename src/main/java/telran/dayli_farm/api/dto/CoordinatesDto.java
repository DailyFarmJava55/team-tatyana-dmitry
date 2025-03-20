package telran.dayli_farm.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CoordinatesDto {
	
	//TODO  validation
	private Double latitude;
	private Double longitude;
}
