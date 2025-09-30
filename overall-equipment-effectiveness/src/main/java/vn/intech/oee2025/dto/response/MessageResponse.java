package vn.intech.oee2025.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL) //no response fields has null value.
public class MessageResponse {	
	private int code;
	private String msg;
	private Object data;
}
