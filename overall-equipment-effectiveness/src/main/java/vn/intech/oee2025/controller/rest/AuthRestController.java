package vn.intech.oee2025.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.intech.oee2025.service.CustomUserDetailService;
import vn.intech.oee2025.util.JwtUtil;
import vn.intech.oee2025.config.Configuation;
import vn.intech.oee2025.dto.UserSecurityDto;
import vn.intech.oee2025.security.AuthRequest;
import vn.intech.oee2025.security.DataResponse;
import vn.intech.oee2025.security.Authorization;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;  
    @Autowired
    private CustomUserDetailService userDetailsService;
    @Autowired
	private Configuation configuation;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) { 	  	 	
    	
    	try {  		
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword() )); 
            SecurityContextHolder.getContext().setAuthentication(authentication);                    
            UserSecurityDto userDetails = (UserSecurityDto) authentication.getPrincipal();           
            String token = jwtUtil.generateToken(userDetails.getUsername());  
            
            // Set HttpOnly cookie
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(true) // set true in prod with HTTPS
                .path("/")
                .maxAge(3600)
                .sameSite("Lax")
                .build();
            response.addHeader("Set-Cookie", cookie.toString());
            
            
            Authorization authorization = new Authorization(token, configuation.getExpireTime(), "Bearer ");         
            return new ResponseEntity<>(new DataResponse(0, "Login successful." ,authorization), HttpStatus.OK);
            
        } catch (UsernameNotFoundException | BadCredentialsException e) {
        	return new ResponseEntity<>(new DataResponse(1, "Username or password incorrect",null), HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(new DataResponse(99, "Invalid credentials",null), HttpStatus.BAD_REQUEST);
        }
    
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
