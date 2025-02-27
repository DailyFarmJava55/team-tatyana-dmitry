package telran.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import lombok.RequiredArgsConstructor;
import telran.accounting.model.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(b->b.disable());
		//.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("**register/**")
				.permitAll()
//				.requestMatchers("/account/user/{login}/role/{role}").hasRole(Role.ADMINISTRATOR.name())
//				.requestMatchers(HttpMethod.PUT, "/account/user/{login}")
//				.access(new WebExpressionAuthorizationManager("#login == authentication.name"))
//				.requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
//				.access(new WebExpressionAuthorizationManager(
//						"#login == authentication.name or hasRole('ADMINISTRATOR')"))
//				.requestMatchers(HttpMethod.POST, "/forum/post/{author}")
//				.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
//				.requestMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}")
//				.access(new WebExpressionAuthorizationManager("#author == authentication.name"))
//				.requestMatchers(HttpMethod.PUT, "/forum/post/{id}")
//				.access((authentication, context) -> new AuthorizationDecision(
//						webSecurity.checkPostAuthor(context.getVariables().get("id"), authentication.get().getName())))
//				.requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
//				.access((authentication, context) -> {
//					boolean checkAuthor = webSecurity.checkPostAuthor(context.getVariables().get("id"),
//							authentication.get().getName());
//					boolean checkModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
//					return new AuthorizationDecision(checkAuthor || checkModerator);
//				})
			.anyRequest().permitAll()
				//.authenticated()
				);
		return http.build();

	}

}






















