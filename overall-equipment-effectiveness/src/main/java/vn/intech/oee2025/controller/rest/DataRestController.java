package vn.intech.oee2025.controller.rest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.intech.oee2025.dto.OeeDto;
import vn.intech.oee2025.repository.DowntimeRepository;
import vn.intech.oee2025.repository.InputRepository;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.security.DataResponse;
import vn.intech.oee2025.service.impl.OeeServiceImpl;

@RestController
@RequestMapping("api/data")
public class DataRestController {

	@Autowired
	private MachineRepository machineRepo;
	@Autowired
	private DowntimeRepository downtimeRepo;
	@Autowired
	private InputRepository inputRepo;
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@RequestMapping(value = "/oee", method = RequestMethod.GET)
	public ResponseEntity<DataResponse> getMonthlyOee(@RequestParam("startDate") Date start, @RequestParam("endDate") Date end){						
		List<OeeDto> oees = oeeService.getMonthlyOee(start, end);		
		DataResponse dtResponse = new DataResponse(0, "data fetched successfully.", oees);	
		return ResponseEntity.ok(dtResponse);
	}
	
}
