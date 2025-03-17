package telran.configuration;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import telran.auth.account.dto.FarmerDto;
import telran.auth.account.model.User;
import telran.auth.security.jwt.farmer.UserDetailsServiceFarmImpl;
import telran.auth.security.jwt.user.UserDetailsServiceUserImpl;

@Configuration
public class ServiceConfiguration {
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration()
									.setFieldMatchingEnabled(true)
									.setFieldAccessLevel(AccessLevel.PRIVATE)
									.setMatchingStrategy(MatchingStrategies.STRICT);

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Qualifier("userAuthenticationManager")
	@Primary
	AuthenticationManager authenticationManagerUser(UserDetailsServiceUserImpl userDetailService,
			PasswordEncoder passwordEncoder) {
		return new ProviderManager(List.of(new DaoAuthenticationProvider() {
			{
				setUserDetailsService(userDetailService);
				setPasswordEncoder(passwordEncoder);
			}
		}));
	}

	@Bean
	@Qualifier("farmAuthenticationManager")
	AuthenticationManager authenticationManagerFarm(UserDetailsServiceFarmImpl farmDetailService,
			PasswordEncoder passwordEncoder) {
		return new ProviderManager(List.of(new DaoAuthenticationProvider() {
			{
				setUserDetailsService(farmDetailService);
				setPasswordEncoder(passwordEncoder);
			}
		}));
	}
}
