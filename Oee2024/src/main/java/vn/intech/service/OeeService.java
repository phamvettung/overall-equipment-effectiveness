package vn.intech.service;

import java.sql.Date;
import java.util.List;

import vn.intech.dto.OeeDto;

public interface OeeService {
	List<OeeDto> readOeeTable(String idMachine, Date start, Date end);
	List<OeeDto> readOeeTrend(Date start, Date end);
}
