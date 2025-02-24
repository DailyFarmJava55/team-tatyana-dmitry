package telran.accounting.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import telran.java55.post.dto.CommentDto;
import telran.java55.post.dto.PostDto;

@Getter
@AllArgsConstructor 
@NoArgsConstructor
public class UserDto {
	String email;
	@Singular
	Set<String> roles;
}
