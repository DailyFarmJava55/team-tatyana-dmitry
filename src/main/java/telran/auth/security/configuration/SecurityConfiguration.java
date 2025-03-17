package telran.auth.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import telran.auth.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.headers(headers->headers.frameOptions(opt->opt.disable()))
		.httpBasic(Customizer.withDefaults())
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
				.requestMatchers("/api/auth/user/register", "/api/auth/user/login").permitAll()
				.requestMatchers("/api/auth/farmer/register", "/api/auth/farmer/login", "/api/auth/farmer/refresh").permitAll()
				.requestMatchers("/api/auth/user/logout", "/api/auth/farmer/logout").authenticated()
				.requestMatchers("/api/farmers/**").hasAuthority("FARMER")
				.requestMatchers("/api/users/**").hasAuthority("USER")
				.anyRequest().authenticated());

		http.addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
		return http.build();
	}

}
