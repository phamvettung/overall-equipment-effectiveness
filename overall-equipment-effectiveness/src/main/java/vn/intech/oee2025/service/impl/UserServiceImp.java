package vn.intech.oee2025.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.oee2025.entity.Account;
import vn.intech.oee2025.repository.UserRepository;
import vn.intech.oee2025.service.UserSevice;

@Service
public class UserServiceImp implements UserSevice {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Account findByUserName(String username) {
		return userRepo.findByUsername2(username);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public Account saveOrUpdate(Account account) {
		return userRepo.save(account);
	}
	
}
