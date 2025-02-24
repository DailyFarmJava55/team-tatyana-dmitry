package telran.auth.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import telran.auth.account.model.Location;

@Data
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String language;
    private String timezone;
    private Location location;
}
