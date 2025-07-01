package vn.intech.oee2025.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.intech.oee2025.entity.Account;


public interface UserRepository extends JpaRepository<Account, String> {	
	
	@Query(value = "SELECT * FROM account acc WHERE acc.username = ?1", nativeQuery = true)
	Optional<Account> findByUsername(String username);
	
}
