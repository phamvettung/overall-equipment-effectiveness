package vn.intech.dto;

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
	private String name;
	private String idMachine;
	private Date date;
	private float f0;
	private float f00;
	private float f1;
	private float f2;
	private float f3;
	private float f4;
	private float f5;
	private float f6;
	private float f7;
	private float f8;
	private float f9;
	private float f10;
	private int din;
	private float a;
	private float p;
	private float q;
	private float oee;
	private float downTime;
	private float runTime;
}
