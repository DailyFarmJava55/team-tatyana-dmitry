package telran.accounting.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	UUID id;
	String email;
	LocalDateTime registrationDate;
	LocalDateTime lastEditDate;
	LocalDateTime lastAccessDate;

}
