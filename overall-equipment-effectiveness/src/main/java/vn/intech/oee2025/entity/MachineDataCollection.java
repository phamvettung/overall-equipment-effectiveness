package vn.intech.oee2025.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MachineDataCollection")
public class MachineDataCollection {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Date")
	private Date date;
	
	@ManyToOne()
	@JoinColumn(
			name = "MachineId",
			referencedColumnName = "id"
	)
	private Machine machine;
	
	@ManyToOne()
	@JoinColumn(
			name = "QCommand",
			referencedColumnName = "command"
	)
	private QCommand qCommand;
	
	@Column(name = "Value")
	private float value;

	public MachineDataCollection() {
		
	}

	public MachineDataCollection(Date date, Machine machine, QCommand qCommand, float value) {
		this.date = date;
		this.machine = machine;
		this.qCommand = qCommand;
		this.value = value;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public QCommand getqCommand() {
		return qCommand;
	}

	public void setqCommand(QCommand qCommand) {
		this.qCommand = qCommand;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	
}
