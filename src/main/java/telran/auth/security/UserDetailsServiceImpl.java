package telran.auth.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.auth.account.model.User;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

		 return org.springframework.security.core.userdetails.User.builder()
				    .username(user.getEmail())
				    .password(user.getPassword())
				    .authorities(user.getRoles().stream()
				        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) 
				        .toList()) 
				    .build();
	    }

	    

}
