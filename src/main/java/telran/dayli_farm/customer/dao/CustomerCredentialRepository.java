package telran.dayli_farm.customer.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import telran.dayli_farm.entity.Customer;
import telran.dayli_farm.entity.CustomerCredential;

@Repository
public interface CustomerCredentialRepository extends JpaRepository<CustomerCredential, UUID> {

	CustomerCredential findByCustomer(Customer customer);


}
