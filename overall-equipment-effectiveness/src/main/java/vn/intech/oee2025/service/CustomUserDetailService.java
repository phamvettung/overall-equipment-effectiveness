package vn.intech.oee2025.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.intech.oee2025.dto.UserSecurityDto;
import vn.intech.oee2025.entity.Account;
import vn.intech.oee2025.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> userOptional = userRepo.findByUsername(username);
		if(userOptional.isPresent()) {
			 UserSecurityDto userSecurity = new UserSecurityDto();
			 userSecurity.setUsername(userOptional.get().getUsername());
			 userSecurity.setPassword(userOptional.get().getPassword());
			 return userSecurity;
		}else {
			throw new UsernameNotFoundException(username);
		}
	}
}
