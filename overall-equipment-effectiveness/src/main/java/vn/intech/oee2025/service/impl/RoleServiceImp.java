package vn.intech.oee2025.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.oee2025.entity.ERole;
import vn.intech.oee2025.entity.Role;
import vn.intech.oee2025.repository.RoleRepository;
import vn.intech.oee2025.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public Optional<Role> findByRoleName(ERole roleName) {
		return roleRepo.findByRoleName(roleName);
	}
	
}
