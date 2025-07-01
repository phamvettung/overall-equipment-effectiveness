package vn.intech.oee2025.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResponse {	
	private int code;
	private String msg;
	private Object data;
}
