package vn.intech.rest.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.intech.dto.OeeDto;
import vn.intech.entity.Machines;
import vn.intech.repositories.MachineRepository;
import vn.intech.service.impl.OeeServiceImpl;

@RestController
@RequestMapping("rest")
public class HomeRestController {

	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@GetMapping("/oee-trend")
	public ResponseEntity<List<OeeDto>>  getOeeTrend(){		
		LocalDate currentDate = LocalDate.now();
		Date startDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1));
		Date endDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
		List<OeeDto> lstOeeOfMonth = this.oeeService.readOeeTrend(startDate, endDate);
		
		return ResponseEntity.ok(lstOeeOfMonth);
	}
}
