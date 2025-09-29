package vn.intech.oee2025.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.intech.oee2025.entity.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Integer> {	
	
	@Query(value = "SELECT * FROM account acc WHERE acc.username = ?1", nativeQuery = true)
	Optional<Account> findByUsername(String username);
	
	@Query(value = "SELECT * FROM account acc WHERE acc.username = ?1", nativeQuery = true)
	Account findByUsername2(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	
}
