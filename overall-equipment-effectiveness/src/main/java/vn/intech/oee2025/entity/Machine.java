package vn.intech.oee2025.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Machine")
public class Machine {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "Code", length = 20, nullable = false)
	private String code;
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String name;
	
	@Column(name = "IpAddress", length = 20, nullable = false)
	private String ipAddress;
	
	@Column(name = "Port", nullable = false)
	private int port;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER, mappedBy="machine")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Input> inputs;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER, mappedBy="machine")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<MachineDataCollection> mdc;

	public Machine() {
		
	}

	public Machine(String code, String name, String ipAddress, int port, List<Input> inputs,
			List<MachineDataCollection> mdc) {
		super();
		this.code = code;
		this.name = name;
		this.ipAddress = ipAddress;
		this.port = port;
		this.inputs = inputs;
		this.mdc = mdc;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	
	public List<MachineDataCollection> getMdc() {
		return mdc;
	}

	public void setMdc(List<MachineDataCollection> mdc) {
		this.mdc = mdc;
	}

}
