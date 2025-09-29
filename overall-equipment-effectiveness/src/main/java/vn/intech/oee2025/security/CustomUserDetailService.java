package vn.intech.oee2025.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.intech.oee2025.dto.UserSecurityDto;
import vn.intech.oee2025.entity.Account;
import vn.intech.oee2025.repository.UserRepository;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> userOptional = userRepo.findByUsername(username);
		if(userOptional.isPresent()) {
			
			 //Method 1: Return UserSecurityDto
			 UserSecurityDto userSecurity = new UserSecurityDto();
			 userSecurity.setUsername(userOptional.get().getUsername());
			 userSecurity.setPassword(userOptional.get().getPassword());	 				 
			 return userSecurity;
			  
			 //Method 2: Return CustomUserDetails
			 //return CustomUserDetails.mapUserToUserDetail(userOptional.get());
			 
		}else {
			throw new UsernameNotFoundException(username);
		}
	}
}
