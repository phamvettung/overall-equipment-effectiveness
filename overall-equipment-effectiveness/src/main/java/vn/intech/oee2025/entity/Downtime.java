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

@Entity
@Table(name = "Downtime")
public class Downtime {
	
	@Id
	@Column(name = "Type", length = 20)
	private String type;
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="downTime")
	private List<Input> inputs;

	public Downtime() {
	}

	public Downtime(String type, String name, List<Input> inputs) {
		this.type = type;
		this.name = name;
		this.inputs = inputs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	@Override
	public String toString() {
		return "Downtime [type=" + type + ", name=" + name + ", inputs=" + inputs + "]";
	}
				
}
