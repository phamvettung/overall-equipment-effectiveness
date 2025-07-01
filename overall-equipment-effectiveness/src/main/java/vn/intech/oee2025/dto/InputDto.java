package vn.intech.oee2025.dto;

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
	private Long id;
	private String downtimeType;
	private int machineId;
	private Date date;
	private Time time;
	private float value;
}
