package telran.dayli_farm.security;

import static telran.dayli_farm.api.ApiConstants.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import telran.dayli_farm.security.service.RevokedTokenService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final RevokedTokenService revokedTokenService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						// .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()

						.requestMatchers(FARMER_REGISTER, FARMER_LOGIN, FARMER_REFRESH_TOKEN,
								CUSTOMER_REGISTER, CUSTOMER_LOGIN, CUSTOMER_REFRESH_TOKEN, GET_ALL_SURPRISE_BAGS,
								"/swagger-ui/**", "/v3/**").permitAll()
						.requestMatchers("/farmer/**").hasRole("FARMER")
						.requestMatchers("/customer/**").hasRole("CUSTOMER")
						.anyRequest().authenticated())
						
				.addFilterBefore(new JwtAuthFilter(jwtService, userDetailsService, revokedTokenService),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService((UserDetailsService) userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
