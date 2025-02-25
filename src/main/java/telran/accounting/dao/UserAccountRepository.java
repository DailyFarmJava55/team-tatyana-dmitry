package telran.accounting.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.accounting.model.UserAccount;


public interface UserAccountRepository extends JpaRepository<UserAccount, UUID>{

	boolean existsByEmail(String email);

}
