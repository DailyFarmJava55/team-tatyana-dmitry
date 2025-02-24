package telran.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import telran.auth.account.dto.FarmerDto;
import telran.auth.account.model.User;



@Configuration
public class ServiceConfiguration {
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
									.setFieldMatchingEnabled(true)
									.setFieldAccessLevel(AccessLevel.PRIVATE)
									.setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.typeMap(User.class, FarmerDto.class).addMappings(mapper -> {
	        mapper.map(User::getLocation, FarmerDto::setLocation);
	    });
		return modelMapper;
		}
		
}
