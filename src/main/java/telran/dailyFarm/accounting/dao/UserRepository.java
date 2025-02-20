package telran.dailyFarm.accounting.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.dailyFarm.accounting.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	User findUserByUsername(String username);
	
	
	
	
} 
