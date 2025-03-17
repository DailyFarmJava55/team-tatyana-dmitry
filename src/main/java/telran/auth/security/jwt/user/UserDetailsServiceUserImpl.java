package telran.auth.security.jwt.user;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.UserRepository;
import telran.utils.exceptions.UserNotFoundException;
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceUserImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		telran.auth.account.model.User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
		 return new User(email, user.getPassword(),
			        AuthorityUtils.createAuthorityList());
	}

}
