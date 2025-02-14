package vn.intech.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Factors")
public class Factors {
	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne()
	@JoinColumn(
			name = "idtype",
			referencedColumnName = "id"
	)
	private LossTypes losstype;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "describe")
	private String describe;
	
	@OneToMany(mappedBy="factor")
	private List<Inputs> inputs;
}
