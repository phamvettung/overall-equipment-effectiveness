package vn.intech.oee2025.security;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	private String accessToken;
	private long expiresIn;
	private String tokenType;
	
	private String username;
	private String email;
}
