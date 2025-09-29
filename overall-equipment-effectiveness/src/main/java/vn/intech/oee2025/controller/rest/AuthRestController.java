package vn.intech.oee2025.controller.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.intech.oee2025.dto.UserSecurityDto;
import vn.intech.oee2025.entity.Account;
import vn.intech.oee2025.entity.ERole;
import vn.intech.oee2025.entity.Role;
import vn.intech.oee2025.security.JwtResponse;
import vn.intech.oee2025.security.LoginRequest;
import vn.intech.oee2025.security.MessageResponse;
import vn.intech.oee2025.security.SignUpRequest;
import vn.intech.oee2025.service.RoleService;
import vn.intech.oee2025.service.UserSevice;
import vn.intech.oee2025.util.JwtUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/auth")
public class AuthRestController {
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserSevice userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
    	Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue();

                    if (jwtUtil.validateToken(refreshToken)) {
                        String username = jwtUtil.extractUsername(refreshToken);
                        String newAccessToken = jwtUtil.generateAccessToken(username);

                        Cookie newAccessCookie = new Cookie("accessToken", newAccessToken);
                        newAccessCookie.setHttpOnly(true);
                        newAccessCookie.setSecure(true);
                        newAccessCookie.setPath("/");
                        newAccessCookie.setMaxAge(3 * 60);

                        response.addCookie(newAccessCookie);

                        return ResponseEntity.ok(new MessageResponse(0, "Token refreshed", null));
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest, HttpServletResponse response) { 	  	 	
    	
    	try {  		
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword() )); 
            SecurityContextHolder.getContext().setAuthentication(authentication);                    
            UserSecurityDto userDetails = (UserSecurityDto) authentication.getPrincipal();           
            String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername()); 
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            
            
            // Set Access Token (short-lived)
            Cookie accessCookie = new Cookie("accessToken", accessToken);
            accessCookie.setHttpOnly(true);  // prevent JS access
            accessCookie.setSecure(true);    // only HTTPS
            accessCookie.setPath("/");
            accessCookie.setMaxAge(1 * 60); // 15 min

            // Set Refresh Token (long-lived)
            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true);
            refreshCookie.setPath("/auth/refresh");
            refreshCookie.setMaxAge(3 * 60); // 7 days

            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);
            
            
            JwtResponse authorization = new JwtResponse(accessToken, jwtUtil.getACCESS_TOKEN_EXPIRATION(), "Bearer ", userDetails.getUsername(), userDetails.getEmail());         
            return new ResponseEntity<>(new MessageResponse(0, "Login successful." ,authorization), HttpStatus.OK);
            
        } catch (UsernameNotFoundException | BadCredentialsException e) {
        	return new ResponseEntity<>(new MessageResponse(1, "Username or password incorrect",null), HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(new MessageResponse(99, "Invalid credentials",null), HttpStatus.BAD_REQUEST);
        }
    
    }
    
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signupRequest) throws Exception{	
		
		if (userService.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse(1, "Username is already.", null));
		}
		
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse(1, "Email is already.", null));
		}
		
		Account account = new Account();
		account.setUsername(signupRequest.getUsername());
		account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		account.setEmail(signupRequest.getEmail());
		account.setFullname(signupRequest.getFullname());
		Set<String> strRoles = signupRequest.getRoles();	
		Set<Role> lstRoles = new HashSet<Role>();
		if(strRoles == null) {
			//default role user
			Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Erorr: Role is not found"));
			lstRoles.add(userRole);
		}else {					
			strRoles.forEach(role -> {				
				switch (role) {
					case "admin": {
						Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
								.orElseThrow(()->new RuntimeException("Erorr: Role is not found"));
						lstRoles.add(adminRole);
						break;
					}
					case "moderator": {
						Role modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
								.orElseThrow(()->new RuntimeException("Erorr: Role is not found"));
						lstRoles.add(modRole);
						break;
					}
					case "user": {
						Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
								.orElseThrow(()->new RuntimeException("Erorr: Role is not found"));
						lstRoles.add(userRole);
						break;
					}				
					default:
						throw new IllegalArgumentException("Unexpected value: " + role);
				}
				
			});			
		}		
		account.setRoles(lstRoles);
		userService.saveOrUpdate(account);
		return ResponseEntity.ok(new MessageResponse(0, "User registered successfully", null));
	}
	
    @GetMapping("/logout")
    public RedirectView  logout(HttpServletRequest request, HttpServletResponse response) {  	
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return new RedirectView("/overall-equipment-effectiveness/security/login.html");
    }
	
}
