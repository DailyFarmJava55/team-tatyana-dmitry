package telran.dailyFarm.security.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.model.Role;
import telran.dailyFarm.security.SecurityService;
import telran.dailyFarm.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final SecurityService securityService;
	@Bean
	AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(userDetailsService.getPasswordEncoder());
		return new ProviderManager(List.of(authProvider));
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize -> authorize
				
				// Public routes
	            .requestMatchers("/swagger-ui/**",  
	                    "/v3/api-docs/**",
	                    "/api/auth/register/user" ,
	                    "/api/auth/register/farmer",
	                    "/api/auth/login", 
	                    "/api/farmers", 
	                    "/api/farmers/city/**", 
	                    "/api/farmers/nearby").permitAll()

	            // ADMIN routes
	            .requestMatchers("/api/users").hasRole(Role.ADMIN.name())

	            // ADMIN or OWNER (delete account by id)
	            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
	                .access((authentication, context) -> 
	                    new AuthorizationDecision(
	                        securityService.isUserOwner(
	                            Long.valueOf(context.getVariables().get("id")),
	                            authentication.get().getName()
	                        ) || authentication.get().getAuthorities().stream()
	                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
	                    )
	                )

	            // OWNER (update users by id)
	            .requestMatchers(HttpMethod.PUT, "/api/users/{id}")
	                .access((authentication, context) -> 
	                    new AuthorizationDecision(
	                        securityService.isUserOwner(
	                            Long.valueOf(context.getVariables().get("id")),
	                            authentication.get().getName()
	                        )
	                    )
	                )

	            // FARMER routes (CRUD for products)
	        //TODO

	            // AUTHENTICATED users (view own profile)
	            .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()

	            // FARMERS can update their own information by id
	            .requestMatchers(HttpMethod.PUT, "/api/farmers/{id}")
	                .access((authentication, context) -> 
	                    new AuthorizationDecision(
	                        securityService.isUserOwner(
	                            Long.valueOf(context.getVariables().get("id")),
	                            authentication.get().getName()
	                        ) && authentication.get().getAuthorities().stream()
	                        .anyMatch(a -> a.getAuthority().equals("ROLE_FARMER"))
	                    )
	                )

	            // Default: any other request requires authentication
	            .anyRequest().authenticated());

	    return http.build();
	}
}
