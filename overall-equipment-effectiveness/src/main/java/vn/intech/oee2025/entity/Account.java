package vn.intech.oee2025.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccountId")
	private int accountId;
	
	@Column(name = "Username", length = 50, unique = true, nullable = false)
	private String username;
	
	@Column(name = "Password", columnDefinition = "VARCHAR(MAX)", nullable = false)
	private String password;
	
	@Column(name= "Created")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date created;
	
	@Column(name = "Fullname", columnDefinition = "NVARCHAR(50)")
	private String fullname;
	
	@Column(name = "Email", columnDefinition = "NVARCHAR(50)", nullable = false, unique = true)
	private String email;
	
	@Column(name="AccountStatus")
	private boolean accountStatus;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="UserRole", joinColumns = @JoinColumn(name = "AccountId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
	private Set<Role> roles = new HashSet<>();
}
