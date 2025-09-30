package vn.intech.oee2025.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.intech.oee2025.entity.Account;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accountId;
	private String username;
	@JsonIgnore
	private String password;
	private String fullname;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	public static CustomUserDetails mapUserToUserDetail(Account user) {
		//Get list roles from Account
		List<GrantedAuthority> listAuthorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())) //scan each role in list roles and cast it into SimpleGrantedAuthority
				.collect(Collectors.toList()); //assign to listAuthorities	
		
		return new CustomUserDetails(user.getAccountId(),
				user.getUsername(),
				user.getPassword(),
				user.getFullname(),
				user.getEmail(),
				listAuthorities);
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
