package vn.intech.oee2025.security;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
	private String username;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created = new Date();
    private String fullname;
    private String email;
    private boolean accountStatus = true;
    private Set<String> roles;
}
