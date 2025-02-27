package telran.security;

import java.util.Collection;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.accounting.dao.UserAccountRepository;
import telran.accounting.model.UserAccount;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	final UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));

		Collection<String> authorities = userAccount.getRoles().stream().map(r -> "ROLE_" + r.name()).toList();

		return new User(email, userAccount.getPassword(), AuthorityUtils.createAuthorityList(authorities));
	}

}
