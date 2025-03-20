package telran.dayli_farm.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.customer.dao.CustomerCredentialRepository;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.entity.CustomerCredential;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl  implements UserDetailsService {
	private final CustomerRepository customerRepo;
	private final CustomerCredentialRepository customerCredentialRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return customerRepo.findByEmail(email)
	            .map(customer -> {
	                CustomerCredential customerCredential = customerCredentialRepo.findByCustomer(customer);
	                
	                return new org.springframework.security.core.userdetails.User(
	                        customer.getEmail(), 
	                        customerCredential.getHashedPassword(), 
	                        List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")) 
	                );
	            })
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}
		

//		Optional<Farmer> farmerOptional = farmerRepo.findByEmail(username);
//		if (farmerOptional.isPresent()) {
//			Farmer farmer = farmerOptional.get();
//			FarmerCredential credential = farmerCredentialRepo.findByFarmer(farmer);
//			return new UserDetailsWithId(farmer.getEmail(), credential.getHashedPassword(),
//					List.of(new SimpleGrantedAuthority("ROLE_FARMER")), farmer.getId());
//		}
//
//		throw new UsernameNotFoundException(USER_NOT_FOUND);
//	}
}

