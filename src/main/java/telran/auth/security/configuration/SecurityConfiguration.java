package telran.auth.security.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import telran.auth.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
	@Bean
	AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(userDetailsService.getPasswordEncoder());
		return new ProviderManager(List.of(authProvider));
	}
	 @Bean
	     PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize -> authorize

				.requestMatchers("/api/auth/user/register", "/api/auth/user/login").permitAll()
				.requestMatchers("/api/auth/farmer/register", "/api/auth/farmer/login").permitAll()

				.requestMatchers("/api/auth/user/logout", "/api/auth/farmer/logout").authenticated()

				.requestMatchers("/api/farmer/**").hasAuthority("FARMER")

				.anyRequest().authenticated());

		return http.build();
	}
}
