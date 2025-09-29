package vn.intech.oee2025.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Role")
public class Role implements Serializable {
	@Id	
	@Column(name = "RoleId", length = 10)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleId;	
	
	@Column(name = "RoleName", columnDefinition = "NVARCHAR(50)")
	@Enumerated(EnumType.STRING)
	private ERole roleName;
	
}
