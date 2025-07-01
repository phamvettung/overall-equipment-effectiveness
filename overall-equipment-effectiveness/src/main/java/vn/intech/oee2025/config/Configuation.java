package vn.intech.oee2025.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Configuration
@Data
public class Configuation {
	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.expiration}")
	private long expireTime;
}
