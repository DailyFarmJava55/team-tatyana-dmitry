package telran.auth.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	 @PersistenceContext
	    private final EntityManager entityManager;

	 @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		  List<GrantedAuthority> authorities = new ArrayList<>();
	        String foundEmail = null;
	        String password = null;

	        try {
	            Object[] userResult = (Object[]) entityManager.createQuery(
	                    "SELECT u.email, u.password FROM User u WHERE u.email = :email")
	                    .setParameter("email", email)
	                    .getSingleResult();

	            foundEmail = (String) userResult[0];
	            password = (String) userResult[1];
	            authorities.add(new SimpleGrantedAuthority("USER"));
	        } catch (NoResultException e) {
	            // User not found, continue to check Farmer table
	        }

	        try {
	            Object[] farmerResult = (Object[]) entityManager.createQuery(
	                    "SELECT f.email, f.password FROM Farmer f WHERE f.email = :email")
	                    .setParameter("email", email)
	                    .getSingleResult();

	            foundEmail = (String) farmerResult[0];
	            password = (String) farmerResult[1];
	            authorities.add(new SimpleGrantedAuthority("FARMER"));
	        } catch (NoResultException e) {
	            // Farmer not found, continue
	        }

	        if (foundEmail == null) {
	            throw new UsernameNotFoundException("User not found: " + email);
	        }

	        return org.springframework.security.core.userdetails.User.builder()
	                .username(foundEmail)
	                .password(password)
	                .authorities(authorities)
	                .build();
	    }
	}