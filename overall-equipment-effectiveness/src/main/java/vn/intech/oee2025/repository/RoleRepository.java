package vn.intech.oee2025.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.intech.oee2025.entity.ERole;
import vn.intech.oee2025.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByRoleName(ERole roleName);
}
