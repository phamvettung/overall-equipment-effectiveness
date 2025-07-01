package vn.intech.oee2025.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Authority", uniqueConstraints = {
		@UniqueConstraint
		(columnNames = {"Username", "RoleId"})
})
public class Authority implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@ManyToOne 
	@JoinColumn(name = "Username", nullable = false)
	private Account account;
	
	@ManyToOne  
	@JoinColumn(name = "RoleId", nullable = false)
	private Role role;
}
