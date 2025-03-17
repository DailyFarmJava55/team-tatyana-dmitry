package telran.auth.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import telran.auth.security.jwt.farmer.FarmJwtFilter;
import telran.auth.security.jwt.user.UserJwtFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
	 @Autowired
     @Qualifier("userDetailsService")
     private UserDetailsService userDetailService;
     @Autowired
     @Qualifier("farmDetailsService")
     private UserDetailsService farmDetailService;
     private final UserJwtFilter userJwtFilter;
     private final FarmJwtFilter farmJwtFilter;
     @Autowired
     @Qualifier("userAuthenticationManager")
     private AuthenticationManager userAuthenticationManager;

     @Autowired
     @Qualifier("farmAuthenticationManager")
     private AuthenticationManager farmAuthenticationManager;

	
     @Bean
     SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
             http.httpBasic(Customizer.withDefaults())
                             .authenticationManager(userAuthenticationManager)
                             .csrf(csrf -> csrf.disable())
                             .userDetailsService(userDetailService)
                             .securityMatcher("/api/auth/user/**", "/api/user/**")
                             .sessionManagement(session -> session
                                             .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                             .authorizeHttpRequests(
                                             authorize -> authorize
                                                             .requestMatchers("/api/auth/user/register",
                                                                             "/api/auth/user/login")
                                                             .permitAll()
                                                             .requestMatchers(HttpMethod.POST,
                                                                             "/api/user/{login}")
                                                             .access(new WebExpressionAuthorizationManager(
                                                                             "#login == authentication.name"))
                                                             .requestMatchers(HttpMethod.DELETE,
                                                                             "/api/user/{login}")
                                                             .access(new WebExpressionAuthorizationManager(
                                                                             "#login == authentication.name"))
                                                             .anyRequest()
                                                             .authenticated())
                             .addFilterBefore(userJwtFilter, BasicAuthenticationFilter.class);
             return http.build();
     }
     @Bean
     SecurityFilterChain farmSecurityFilterChain(HttpSecurity http) throws Exception {
             http.httpBasic(Customizer.withDefaults())
                             .authenticationManager(farmAuthenticationManager)
                             .csrf(csrf -> csrf.disable())
                             .userDetailsService(farmDetailService)
                             .securityMatcher("/api/auth/farm/**", "/api/farm/**")
                             .sessionManagement(session -> session
                                             .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                             .authorizeHttpRequests(
                                             authorize -> authorize
                                                             .requestMatchers("/api/auth/farm/register",
                                                                             "/api/auth/farm/login")
                                                             .permitAll()
                                                             .requestMatchers(HttpMethod.DELETE, "/api/farm/{login}")
                                                             .access(new WebExpressionAuthorizationManager(
                                                                             "#login == authentication.name"))
                                                             .anyRequest().authenticated())
                             .addFilterBefore(farmJwtFilter, BasicAuthenticationFilter.class);
             return http.build();
     }

}
