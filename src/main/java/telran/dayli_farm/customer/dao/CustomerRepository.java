package telran.dayli_farm.customer.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.customer.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	
	boolean existsByEmail(String email);

	Optional<Customer> findByEmail(String username);

	Optional<Customer> findById(UUID id);
}
