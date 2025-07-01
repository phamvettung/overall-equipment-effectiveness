package vn.intech.oee2025.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineDataDto {
	private String machineName;
	private int machineId;
	private float q200;
	private float q201;
	private float q300;
	private float q301;
	private float q500;	
}
