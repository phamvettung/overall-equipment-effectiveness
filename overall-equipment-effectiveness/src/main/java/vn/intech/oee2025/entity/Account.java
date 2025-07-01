package vn.intech.oee2025.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account implements Serializable {
	
	@Id
	@Column(name = "Username", length = 50)
	private String username;
	
	@Column(name = "Password", columnDefinition = "VARCHAR(MAX)", nullable = false)
	private String password;
	
	@Column(name = "Fullname", columnDefinition = "NVARCHAR(50)")
	private String fullname;
	
	@Column(name = "Email", columnDefinition = "NVARCHAR(50)")
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
}
