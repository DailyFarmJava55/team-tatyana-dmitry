package telran.dailyFarm.accounting.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dailyFarm.accounting.dto.farmer.AddressDto;
import telran.dailyFarm.accounting.dto.farmer.LocationDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerRegisterDto {
	@NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    private String farmName;

    @NotNull(message = "Address is required")
    private AddressDto address;

    @NotNull(message = "Location is required")
    private LocationDto location;
}