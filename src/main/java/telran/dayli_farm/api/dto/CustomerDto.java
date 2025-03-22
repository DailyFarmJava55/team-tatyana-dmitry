package telran.dayli_farm.api.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.entity.Customer;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {
	 private UUID id;
	 private String email;
	 private String firstName;
	 private String lastName;
	 private String phone;
	 private String city;
	 
	 
	 
		public static CustomerDto of(Customer customer) {
			return CustomerDto.builder()
					.id(customer.getId())
					.email(customer.getEmail())
					.firstName(customer.getFirstName())
					.lastName(customer.getLastName())
					.phone(customer.getPhone())
					.city(customer.getCity())
					.build();
		}
	
	 
}
