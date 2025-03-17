package telran.auth.security.jwt.farmer;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.auth.account.dao.FarmerRepository;
import telran.auth.account.model.Farmer;
import telran.utils.exceptions.FarmerNotFoundException;

@Service("farmDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceFarmImpl implements UserDetailsService {
	
	private final FarmerRepository farmerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Farmer farmer = farmerRepository.findByEmail(email).orElseThrow(() -> new FarmerNotFoundException(email));
		 return new User(email, farmer.getPassword(),
			        AuthorityUtils.createAuthorityList());
	}

}
