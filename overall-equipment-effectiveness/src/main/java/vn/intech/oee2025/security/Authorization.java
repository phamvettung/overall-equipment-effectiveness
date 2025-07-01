package vn.intech.oee2025.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authorization {
	private String accessToken;
	private long expiresIn;
	private String tokenType;
}
