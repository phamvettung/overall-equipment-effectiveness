package vn.intech.oee2025.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineDto {
	private Integer id;
	private String code;
	private String name;
	private String ipAddress;
	private int port;
}
