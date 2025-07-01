package vn.intech.oee2025.security;

import lombok.Data;

@Data
public class AuthRequest {
	private String username;
    private String password;
    
	@Override
	public String toString() {
		return "AuthRequest [username=" + username + ", password=" + password + "]";
	}
}
