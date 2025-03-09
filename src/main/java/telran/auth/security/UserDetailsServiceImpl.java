package telran.auth.security;

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
	        try {
	            Object[] result = (Object[]) entityManager.createQuery(
	                    "SELECT u.email, u.password FROM User u WHERE u.email = :email " +
	                    "UNION " +
	                    "SELECT f.email, f.password FROM Farmer f WHERE f.email = :email")
	                    .setParameter("email", email)
	                    .getSingleResult();

	            String foundEmail = (String) result[0];
	            String password = (String) result[1];

	            return org.springframework.security.core.userdetails.User.builder()
	                    .username(foundEmail)
	                    .password(password)
	                    .build();
	        } catch (NoResultException e) {
	            throw new UsernameNotFoundException("User not found: " + email);
	        }
	    }
	}