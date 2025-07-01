package vn.intech.oee2025.controller;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import vn.intech.oee2025.dto.InputDto;
import vn.intech.oee2025.entity.Downtime;
import vn.intech.oee2025.entity.Input;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.mapper.InputMapper;
import vn.intech.oee2025.repository.DowntimeRepository;
import vn.intech.oee2025.repository.InputRepository;
import vn.intech.oee2025.repository.MachineRepository;


@Controller
@RequestMapping("input")
public class InputController {		
	@Autowired
	private HttpServletRequest request;	
	@Autowired
	private DowntimeRepository downtimeRepo;
	@Autowired
	private InputMapper inputMapper;
	@Autowired
	private InputRepository inputRepo;
	@Autowired
	private MachineRepository machineRepo;
	
	private String machineId = "";	
	
	@GetMapping()
	public String index(
			Model model, 
			@PathParam("mid") String mid, 
			@PathParam("mname") String mname){		
		
		List<Downtime> downtimes = downtimeRepo.findAll();	
		this.machineId = mid;
		model.addAttribute("machineName", mname);
		model.addAttribute("downtimes", downtimes);
	
		return "input";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> saveInput() {		
		JSONArray jsonArrayReceived = (JSONArray) JSONValue.parse(request.getParameter("param"));
		List<InputDto> inputDtos = new ArrayList<InputDto>();	
		for(int i = 0; i < jsonArrayReceived.size(); i++) {
			JSONObject rec = (JSONObject) jsonArrayReceived.get(i);
			Timestamp dateTime = Timestamp.valueOf(rec.get("date").toString());
			Date date = new Date(dateTime.getTime());
			Time time = new Time(dateTime.getTime());
			
			InputDto inputDto = new InputDto();
			inputDto.setMachineId(Integer.parseInt(this.machineId));
			inputDto.setDowntimeType(rec.get("type").toString());;
			inputDto.setDate(date);	
			inputDto.setTime(time);
			inputDto.setValue(Float.parseFloat(rec.get("value").toString()));		
			
			Input entity = this.inputMapper.convertToEntity(inputDto);						
			this.inputRepo.save(entity);	
		}	
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
