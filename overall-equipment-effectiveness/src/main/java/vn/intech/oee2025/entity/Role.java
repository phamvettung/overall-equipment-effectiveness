package vn.intech.oee2025.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Role")
public class Role implements Serializable {
	@Id	
	@Column(name = "Id", length = 10)
	private String id;	
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(50)")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Authority> authorities;
}
