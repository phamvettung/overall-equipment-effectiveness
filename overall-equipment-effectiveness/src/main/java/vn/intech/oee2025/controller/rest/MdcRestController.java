package vn.intech.oee2025.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.intech.oee2025.dto.MachineDataDto;
import vn.intech.oee2025.dto.response.MessageResponse;
import vn.intech.oee2025.dto.MachineDataCollectionDto;
import vn.intech.oee2025.entity.MachineDataCollection;
import vn.intech.oee2025.entity.QCommand;
import vn.intech.oee2025.exception.ErrorCode;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.repository.MdcRepository;
import vn.intech.oee2025.repository.QCommandRepository;

@RestController
@RequestMapping("api/mdc")
public class MdcRestController {

	@Autowired
	private MdcRepository mdcRepo;
	@Autowired
	private MachineRepository machineRepo;
	@Autowired
	private QCommandRepository qCommandRepo;
	
	@PostMapping(value = "/save")
	public ResponseEntity<MessageResponse> save(@RequestBody MachineDataCollectionDto dto) throws Exception{
				
		Machine machine = machineRepo.findById(dto.getMachineId())
				.orElseThrow(() -> new RuntimeException("Machine not found"));
		QCommand qCommand = qCommandRepo.findById(dto.getQCommand())
				.orElseThrow(() -> new RuntimeException("Command not found"));
		
		MachineDataCollection mdc = new MachineDataCollection();
		mdc.setMachine(machine);
		mdc.setDate(dto.getDate());
		mdc.setqCommand(qCommand);
		mdc.setValue(dto.getValue());			
		MachineDataCollection savedMdc = mdcRepo.save(mdc);
		
		ErrorCode errorCode = ErrorCode.SUCCESS;
		return ResponseEntity.status(errorCode.getStatusCode()).body(new MessageResponse(errorCode.getCode(), errorCode.getMessage(), savedMdc));
	}
	
	
	
}
