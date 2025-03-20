package telran.dayli_farm.api.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenResponseDto {

	private String accessToken;

	private String refreshToken;
}
