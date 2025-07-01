package vn.intech.oee2025.service;

import java.sql.Date;
import java.util.List;

import vn.intech.oee2025.dto.MachineDataDto;
import vn.intech.oee2025.dto.OeeDto;

public interface OeeService {
	List<OeeDto> getDailyOee(String machineId, Date start, Date end);
	List<OeeDto> getMonthlyOee(Date start, Date end);
	List<MachineDataDto> getMDC(Date start, Date end);
}
