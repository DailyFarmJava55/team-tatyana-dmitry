package telran.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.model.farmer.Farmer;


@Configuration
public class ServiceConfiguration {
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
									.setFieldMatchingEnabled(true)
									.setFieldAccessLevel(AccessLevel.PRIVATE)
									.setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper;
		}
		@Bean
		PasswordEncoder getPasswordEncoder() {
			return new BCryptPasswordEncoder();
		
	}
}
