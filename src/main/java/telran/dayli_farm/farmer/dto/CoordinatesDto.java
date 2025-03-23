package telran.dayli_farm.farmer.dto;

import static telran.dayli_farm.api.message.ErrorMessages.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.dayli_farm.farmer.entity.Coordinates;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CoordinatesDto {
	
	@NotNull(message = LATITUDE_REQUIRED)
	@Min(value = -90, message = INVALID_LATITUDE)
	@Max(value = 90, message = INVALID_LATITUDE)
	private Double latitude;
	
	@NotNull(message = LONGITUDE_REQUIRED)
	@Min(value = -180, message = INVALID_LONGITUDE)
	@Max(value = 180, message = INVALID_LONGITUDE)
	private Double longitude;

	public static CoordinatesDto of(Coordinates coordinates) {
		 return CoordinatesDto.builder()
	                .latitude(coordinates.getLatitude())
	                .longitude(coordinates.getLongitude())
	                .build();
	}
}
