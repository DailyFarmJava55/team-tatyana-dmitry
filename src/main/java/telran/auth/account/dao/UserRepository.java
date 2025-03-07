package telran.auth.account.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import telran.auth.account.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.role = 'USER'")
	List<User> findAllUsers();

	@Query("SELECT u FROM User u WHERE u.role = 'FARMER'")
	List<User> findAllFarmers();

	@Query("SELECT u FROM User u WHERE u.farmName = ?1")	
	Optional<User> findByFarmName(String farmName);
}
