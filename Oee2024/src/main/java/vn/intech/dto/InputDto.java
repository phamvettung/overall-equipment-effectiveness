package vn.intech.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InputDto {
	private int id;
	private String idFactor;
	private String idMachine;
	private float value;
	private Date date;
	private Time time;
	private String unit;
	private String userName;
}
