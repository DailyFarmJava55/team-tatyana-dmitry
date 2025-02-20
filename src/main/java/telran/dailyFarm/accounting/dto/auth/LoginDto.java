package telran.dailyFarm.accounting.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dailyFarm.accounting.model.Role;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	private String username;
	private String email;
	private Role role;
}
