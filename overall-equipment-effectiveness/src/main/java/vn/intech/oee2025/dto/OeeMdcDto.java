package vn.intech.oee2025.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OeeMdcDto {
	private String machineName;
	private int machineId;
	private float downtime;
	private float runtime;
	private float a;
	private float p;
	private float q;
	private float oee;
	private float q200;
	private float q201;
	private float q500;	
}
