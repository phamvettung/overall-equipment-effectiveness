package vn.intech.oee2025.service;
import vn.intech.oee2025.entity.ERole;
import vn.intech.oee2025.entity.Role;
import java.util.Optional;

public interface RoleService {
	Optional<Role> findByRoleName(ERole roleName);
}
