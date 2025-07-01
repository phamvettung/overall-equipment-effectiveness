package vn.intech.oee2025.entity;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Input")
public class Input {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(
			name = "DowntimeType",
			referencedColumnName = "type"
	)
	private Downtime downTime;
	
	@ManyToOne()
	@JoinColumn(
			name = "MachineId",
			referencedColumnName = "id"
	)
	private Machine machine;
	
	@Column(name = "Date", nullable = false)
	private Date date;
	
	@Column(name = "Time", nullable = false)
	private Time time;
	
	@Column(name = "Value", nullable = false)
	private float value;

	public Input() {
		
	}

	public Input(Downtime downTime, Machine machine, Date date, Time time, float value) {
		this.downTime = downTime;
		this.machine = machine;
		this.date = date;
		this.time = time;
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Downtime getDownTime() {
		return downTime;
	}

	public void setDownTime(Downtime downTime) {
		this.downTime = downTime;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Input [id=" + id + ", downTime=" + downTime + ", machine=" + machine + ", date=" + date + ", time="
				+ time + ", value=" + value + "]";
	}
			
}
