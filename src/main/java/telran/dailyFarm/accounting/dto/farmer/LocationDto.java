package telran.dailyFarm.accounting.dto.farmer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LocationDto {
	@NotNull(message = "Latitude is required")
	private Double latitude;

	@NotNull(message = "Longitude is required")
	private Double longitude;
}
