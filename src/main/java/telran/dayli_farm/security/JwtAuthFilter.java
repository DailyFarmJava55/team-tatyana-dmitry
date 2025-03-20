package telran.dayli_farm.security;

import static telran.dayli_farm.api.ApiConstants.CUSTOMER_CHANGE_EMAIL;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_LOGIN;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_REFRESH_TOKEN;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_REGISTER;
import static telran.dayli_farm.api.ApiConstants.CUSTOMER_RESET_PASSWORD;
import static telran.dayli_farm.api.ApiConstants.FARMER_CHANGE_EMAIL;
import static telran.dayli_farm.api.ApiConstants.FARMER_REFRESH_TOKEN;
import static telran.dayli_farm.api.ApiConstants.FARMER_REGISTER;
import static telran.dayli_farm.api.ApiConstants.RESET_PASSWORD;
import static telran.dayli_farm.api.message.ErrorMessages.INVALID_TOKEN;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.security.service.RevokedTokenService;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final RevokedTokenService revokedTokenService;
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.info("🚀 [JwtAuthFilter] - Incoming request: {}", requestURI);
        
        if (isPublicEndpoint(requestURI)) {
            log.info("✅ [JwtAuthFilter] - Request does not require a token. Path: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (revokedTokenService.isRevorkedToken(token)) {
                log.warn("❌ [JwtAuthFilter] - Token is blacklisted: {}", token);
                throw new JwtException(INVALID_TOKEN);
            }

            if (!jwtService.validateToken(token)) {
                log.warn("❌ [JwtAuthFilter] - Token is invalid: {}", token);
                throw new JwtException(INVALID_TOKEN);
            }

            if (jwtService.getExpirationDate(token).before(new Date())) {
                log.warn("❌ [JwtAuthFilter] - Token is expired: {}", token);
                throw new JwtException(INVALID_TOKEN);
            }

            String email = jwtService.extractEmail(token);
            log.info("📧 [JwtAuthFilter] - Extracted email from token: {}", email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("👤 [JwtAuthFilter] - User details loaded successfully for email: {}", email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("🔐 [JwtAuthFilter] - User authenticated successfully: {}", email);

        } catch (JwtException e) {
            log.error("❌ [JwtAuthFilter] - Token processing failed: {}", e.getMessage());
            request.setAttribute("JWT_ERROR", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.equals(FARMER_REFRESH_TOKEN) 
                || requestURI.equals(RESET_PASSWORD)
                || requestURI.equals(FARMER_CHANGE_EMAIL)
                || requestURI.equals(FARMER_REGISTER)
                || requestURI.equals(CUSTOMER_REFRESH_TOKEN)
                || requestURI.equals(CUSTOMER_RESET_PASSWORD)
                || requestURI.equals(CUSTOMER_CHANGE_EMAIL)
                || requestURI.equals(CUSTOMER_REGISTER)
                || requestURI.equals(CUSTOMER_LOGIN)
                || requestURI.equals("/swagger-ui.html")
                || requestURI.startsWith("/swagger")
                || requestURI.startsWith("/v3");
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