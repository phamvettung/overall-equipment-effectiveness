package vn.intech.oee2025.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.access_token_expiration}")
	private long ACCESS_TOKEN_EXPIRATION;
	
	@Value("${jwt.refresh_token_expiration}")
	private long REFRESH_TOKEN_EXPIRATION;
	
	// Generate JWT from user name
	public String generateAccessToken(String username) {
		log.info("secretKey: {}", SECRET_KEY);
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }
	
	public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
	
	public String extractUsername(String token) {		
		Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(60)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

	// validate Token
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
    public boolean isTokenExpired(String token) {
    	try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .setAllowedClockSkewSeconds(60)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
    		
    	}catch(MalformedJwtException ex) {
    		log.error("Invalid Jwt token");
    	}catch(ExpiredJwtException ex) {
    		log.error("Expired Jwt token");
    	}catch(UnsupportedJwtException ex) {
    		log.error("Unsupported Jwt token");
    	}catch(IllegalArgumentException ex) {
    		log.error("Jwt claims String is empty");
    	} 	
    	return false;
    }
}
