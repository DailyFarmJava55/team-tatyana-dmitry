package telran.auth.account.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import telran.auth.account.model.Role;

@Getter
@AllArgsConstructor
public class AuthResponse {
	private String email;
	private Set<Role>roles;
	private String token;
}
