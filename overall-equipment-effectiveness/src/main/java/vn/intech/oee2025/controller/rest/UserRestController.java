package vn.intech.oee2025.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.intech.oee2025.entity.Account;
import vn.intech.oee2025.repository.UserRepository;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {

	@Autowired
	private UserRepository userRepo;	
    @Autowired
    private PasswordEncoder passwordEncoder;
      
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody Account user) throws Exception{	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return new ResponseEntity<>("New user added.", HttpStatus.OK);
	}
}
