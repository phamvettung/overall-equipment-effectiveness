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
public class MachineDataCollectionDto {
	private Long id;
	private int machineId;
	private String qCommand;
	private Date date;
	private float value;
	
	@Override
	public String toString() {
		return "MdcDto [id=" + id + ", machineId=" + machineId + ", qCommand=" + qCommand + ", date=" + date
				+ ", value=" + value + "]";
	}
		
}
