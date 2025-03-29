package telran.dayli_farm.security;

import static telran.dayli_farm.api.ApiConstants.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.security.service.RevokedTokenService;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final RevokedTokenService revokedTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		log.info("OncePerRequestFilter. requestURI" + requestURI);
		if (requestURI.equals(FARMER_REFRESH_TOKEN) 
				
				|| requestURI.equals(RESET_PASSWORD)
				|| requestURI.equals(FARMER_REGISTER)
				||requestURI.equals(FARMER_REFRESH_TOKEN)
				|| requestURI.equals(CUSTOMER_REFRESH_TOKEN)
				|| requestURI.equals(CUSTOMER_REGISTER)
				|| requestURI.equals(CUSTOMER_LOGIN)
				|| requestURI.equals(CUSTOMER_REGISTER)
				|| requestURI.equals(CUSTOMER_LOGIN)
				|| requestURI.startsWith("/paypal")
				|| requestURI.equals("/swagger-ui.html") 
				|| requestURI.startsWith("/swagger") 
				|| requestURI.startsWith("/v3")) {
			log.info("OncePerRequestFilter. Request does not need token");
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = getTokenFromRequest(request);

		if (token == null || revokedTokenService.isRevorkedToken(token) || !jwtService.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		String email = jwtService.extractEmail(token);
		String role = jwtService.extractRole(token);

		        
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if (jwtService.validateToken(token)) {
				Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		log.info("📥 [JwtAuthFilter] - Token received from header: {}", header);

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			log.info("📝 [JwtAuthFilter] - Token extracted successfully: {}", token);
			return token;
		}

		return null;
	}

}