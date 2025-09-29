package vn.intech.oee2025.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;

import vn.intech.oee2025.dto.UserSecurityDto;
import vn.intech.oee2025.util.JwtUtil;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService userDetaislService;

	@Override
    protected void doFilterInternal(
    		@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
   ) throws ServletException, IOException {
		try {
			String token = null, username = null;
			// Check whether if header authorization have JWT or not.
	        String authHeader = request.getHeader("Authorization");
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	        }
	        
	        // If not in header, try to get from cookie
	        if (token == null) {
	            token = getJwtFromCookies(request);
	        }     
	        if (token != null) {
	            username = jwtUtil.extractUsername(token);
	        }
	                 
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {        	       	
	            UserSecurityDto userDetails = (UserSecurityDto)userDetaislService.loadUserByUsername(username);
	            if (jwtUtil.isTokenValid(token, userDetails)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());

	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	                                
	            }
	        }
	        filterChain.doFilter(request, response);
		}catch (Exception e) {
			log.error("fail on set user authentication. ", e);
		}
    }
	
	
	private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
	
}
