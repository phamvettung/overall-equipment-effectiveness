package vn.intech.oee2025.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.intech.oee2025.dto.InputDto;
import vn.intech.oee2025.entity.Downtime;
import vn.intech.oee2025.entity.Input;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.DowntimeRepository;
import vn.intech.oee2025.repository.InputRepository;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.security.DataResponse;

@RestController
@RequestMapping("api/input")
public class InputRestController {

	@Autowired
	private InputRepository inputRepo;
	@Autowired
	private DowntimeRepository downtimeRepo;
	@Autowired
	private MachineRepository machineRepo;
	
	@PostMapping(value = "/save")
	public ResponseEntity<DataResponse> save(@RequestBody InputDto dto) throws Exception{
		Downtime downtime = downtimeRepo.findById(dto.getDowntimeType())
                .orElseThrow(() -> new RuntimeException("Downtime not found"));
		Machine machine = machineRepo.findById(dto.getMachineId())
                .orElseThrow(() -> new RuntimeException("Machine not found"));
		
		Input input = new Input();
	    input.setDownTime(downtime);
	    input.setMachine(machine);
	    input.setDate(dto.getDate());
	    input.setTime(dto.getTime());
	    input.setValue(dto.getValue());
	    
	    Input savedInput = inputRepo.save(input);
	    DataResponse dtResponse = new DataResponse(0, "saved successfully.", savedInput);
	    
		return ResponseEntity.ok(dtResponse);
	}
}
