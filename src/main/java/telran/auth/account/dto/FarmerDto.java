package telran.auth.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import telran.auth.account.model.Location;

@Data
@AllArgsConstructor
public class FarmerDto {
    private String email;
    private String password;
    private String farmName;
    private String language;
    private String timezone;
    private Location location;
   
}
