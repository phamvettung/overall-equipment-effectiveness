package vn.intech.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import vn.intech.dto.InputDto;
import vn.intech.entity.Factors;
import vn.intech.entity.Inputs;
import vn.intech.entity.Machines;
import vn.intech.mapper.InputMapper;
import vn.intech.repositories.FactorRepository;
import vn.intech.repositories.InputRepository;

@Controller
@RequestMapping("input-page")
public class InputController {

	@Autowired
	private FactorRepository factorRepo;
	@Autowired 
	private InputRepository inputRepo;
	@Autowired
	private InputMapper inputMapper;
	@Autowired 
	private HttpServletRequest request;
	
	private String machineId = "";
	
	@GetMapping()
	public String index(
			Model model,
			@PathParam("mid") String mid, 
			@PathParam("mname") String mname) {
		List<Factors> lstFactor = this.factorRepo.findAll();
		
		this.machineId = mid;
		model.addAttribute("machineName", mname);
		model.addAttribute("lstFactor", lstFactor);
		
		request.setAttribute("namePage", "Nhập liệu");
		
		return "input";
	}
	
	@PostMapping()
	public ResponseEntity<String> saveInput() {
		JSONArray jsonArrayReceived = (JSONArray) JSONValue.parse(request.getParameter("param"));
		List<InputDto> lstInputDto = new ArrayList<InputDto>();	
		for(int i = 0; i < jsonArrayReceived.size(); i++) {
			JSONObject rec = (JSONObject) jsonArrayReceived.get(i);
			Timestamp dateTime = Timestamp.valueOf(rec.get("date").toString());
			Date date = new Date(dateTime.getTime());
			Time time = new Time(dateTime.getTime());
			
			InputDto inputDto = new InputDto();
			inputDto.setIdMachine(machineId);
			inputDto.setIdFactor(rec.get("factorId").toString());
			inputDto.setDate(date);	
			inputDto.setTime(time);
			inputDto.setValue(Float.parseFloat(rec.get("value").toString()));
			inputDto.setUnit("phut");
			inputDto.setUserName("admin");
			lstInputDto.add(inputDto);	
			
			Inputs entity = this.inputMapper.convertToEntity(inputDto);
			this.inputRepo.save(entity);
			
		}	
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
