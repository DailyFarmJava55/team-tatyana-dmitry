package telran.dayli_farm.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.customer.entity.CustomerCredential;
import telran.dayli_farm.farmer.dao.FarmerCredentialRepository;
import telran.dayli_farm.farmer.dao.FarmerRepository;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.farmer.entity.FarmerCredential;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final CustomerRepository customerRepo;
	private final CustomerCredentialRepository customerCredentialRepo;
	private final FarmerRepository farmerRepo;
	private final FarmerCredentialRepository farmerCredentialRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Customer> customerOptional = customerRepo.findByEmail(email);

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			CustomerCredential customerCredential = customerCredentialRepo.findByCustomer(customer);

			return new CustomUserDetailService(customer.getEmail(), customerCredential.getHashedPassword(),
					List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")), customer.getId());
		}

		Optional<Farmer> farmerOptional = farmerRepo.findByEmail(email);

		if (farmerOptional.isPresent()) {
			Farmer farmer = farmerOptional.get();
			FarmerCredential farmerCredential = farmerCredentialRepo.findByFarmer(farmer);

			return new CustomUserDetailService(farmer.getEmail(), farmerCredential.getHashedPassword(),
					List.of(new SimpleGrantedAuthority("ROLE_FARMER")), farmer.getId());
		}

		throw new UsernameNotFoundException("User not found");
	}
}
