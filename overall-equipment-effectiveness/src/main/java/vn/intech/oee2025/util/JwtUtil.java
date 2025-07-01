package vn.intech.oee2025.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.intech.oee2025.config.Configuation;

@Component
public class JwtUtil {

	//private static final String SECRET_KEY = "$2a$10$E5wcu9erYXaHipc6QTTff.Zq0OFRNA70uY24MPKFM/hQYcsfxGN1q";
	
	@Autowired
	private Configuation configuation;
	
	public String generateToken(String username) {
		//log.info("secretKey: {}", configuation.getSecretKey());
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + configuation.getExpireTime()))
            .signWith(SignatureAlgorithm.HS512, configuation.getSecretKey())
            .compact();
    }
	
	public String extractUsername(String token) {		
		Claims claims = Jwts.parser()
                .setSigningKey(configuation.getSecretKey())
                .setAllowedClockSkewSeconds(60)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(configuation.getSecretKey())
                .setAllowedClockSkewSeconds(60)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
    
    public boolean validateToken(String token, String username) {
    	return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
    
    
    
//	public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .signWith(generateKey())
//                .compact();
//    }
//	private SecretKey generateKey() {
//    	byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
//    	return Keys.hmacShaKeyFor(decodedKey);
//    }
	
}
