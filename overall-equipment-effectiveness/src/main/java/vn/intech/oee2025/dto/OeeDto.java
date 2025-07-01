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
public class OeeDto {
	private String machineName;
	private int machineId;
	private Date date;
	private double f0;
	private double f00;
	private double f1;
	private double f2;
	private double f3;
	private double f4;
	private double f5;
	private double f6;
	private double f7;
	private double f8;
	private double f9;
	private double f10;
	private int din;
	private float downtime;
	private float runtime;
	private float a;
	private float p;
	private float q;
	private float oee;
}
