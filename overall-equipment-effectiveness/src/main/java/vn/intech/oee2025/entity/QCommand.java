package vn.intech.oee2025.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QCommand")
public class QCommand {	
	@Id
	@Column(name = "Command", length = 10)
	private String command;
	
	@Column(name = "Definition", columnDefinition = "NVARCHAR(255)", nullable = false)
	private String definition;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="qCommand")
	private List<MachineDataCollection> machineDataCollection;
}
