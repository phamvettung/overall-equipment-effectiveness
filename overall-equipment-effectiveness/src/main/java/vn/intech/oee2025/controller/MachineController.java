package vn.intech.oee2025.controller;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.intech.oee2025.dto.MachineDto;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.mapper.MachineMapper;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.service.MachineService;

@Controller
@RequestMapping("machine")
public class MachineController {
			
	@Autowired
	private MachineRepository machineRepo;	
	@Autowired
	private HttpServletRequest request;	
	@Autowired
	private HttpServletResponse response;	
	@Autowired
	private MachineMapper machineMapper;
	@Autowired
	private MachineService machineService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		List<Machine> machines = new ArrayList<>();
		machines = machineRepo.findAll();	
		model.addAttribute("machines", machines);	
		return "machine";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createMachine() throws IOException{	
		JSONObject rec = (JSONObject) JSONValue.parse(request.getParameter("param"));
		String machineCodeReceived= rec.get("machineCode").toString();
		String machineNameReceived = rec.get("machineName").toString();
		String ipAddressReceived= rec.get("ipAddress").toString();
		String portReceived = rec.get("port").toString();
		
		int port = Integer.parseInt(portReceived);
		
		MachineDto machineDto = new MachineDto();
		machineDto.setCode(machineCodeReceived);
		machineDto.setName(machineNameReceived);
		machineDto.setIpAddress(ipAddressReceived);
		machineDto.setPort(port);	
		Machine entity = this.machineMapper.convertToEntity(machineDto);
		machineRepo.save(entity);
		
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();
		List<Machine> machines = new ArrayList<>();
		machines = machineRepo.findAll();
		out.println("<tbody>");
		for (Machine machine : machines) {
			out.print("<tr>\r\n"
					+ "		<td>"+machine.getId()+"</td>\r\n"
					+ "		<td>"+machine.getCode()+"</td>\r\n"
					+ "		<td>"+machine.getName()+"</td>\r\n"
					+ "		<td>"+machine.getIpAddress()+"</td>\r\n"
					+ "		<td>"+machine.getPort()+"</td>\r\n"
					+ "		<td>\r\n"
					+ "			<button>Edit</button>\r\n"
					+ "			<button onclick='removeMachine("+machine.getId()+")'>Remove</button>\r\n"
					+ "		</td>\r\n"
					+ "</tr>");
		}	
		out.println("</tbody>");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/remove-machine/{id}")
	public ResponseEntity<String> removeMachine(@PathVariable("id") Integer id) throws IOException{	
			
			
//		Machine entity = machineRepo.findById(id)
//		        .orElseThrow(() -> new RuntimeException("Machine not found"));
//	    machineRepo.delete(entity);
	    
	    machineService.removeMachineById(id);
	    
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();
		List<Machine> machines = new ArrayList<>();
		machines = machineRepo.findAll();
		out.println("<tbody>");
		for (Machine machine : machines) {
			out.print("<tr>\r\n"
					+ "		<td>"+machine.getId()+"</td>\r\n"
					+ "		<td>"+machine.getCode()+"</td>\r\n"
					+ "		<td>"+machine.getName()+"</td>\r\n"
					+ "		<td>"+machine.getIpAddress()+"</td>\r\n"
					+ "		<td>"+machine.getPort()+"</td>\r\n"
					+ "		<td>\r\n"
					+ "			<button>Edit</button>\r\n"
					+ "			<button onclick='removeMachine("+machine.getId()+")'>Remove</button>\r\n"
					+ "		</td>\r\n"
					+ "</tr>");
		}	
		out.println("</tbody>");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/update-machine")
	public ResponseEntity<String> updateMachine() throws IOException{				
		
		JSONObject rec = (JSONObject) JSONValue.parse(request.getParameter("param"));
		String machineIdReceived= rec.get("machineId").toString();
		String machineCodeReceived= rec.get("machineCode").toString();
		String machineNameReceived = rec.get("machineName").toString();
		String ipAddressReceived= rec.get("ipAddress").toString();
		String portReceived = rec.get("port").toString();
		
		int id = Integer.parseInt(machineIdReceived);
		int port = Integer.parseInt(portReceived);
		
		Optional<Machine> optionalMachine = machineRepo.findById(id);
        if (optionalMachine.isPresent()) {
            Machine m = optionalMachine.get();
            m.setCode(machineCodeReceived);
            m.setName(machineNameReceived);
            m.setIpAddress(ipAddressReceived);
            m.setPort(port);
            machineRepo.save(m); // saves the updated entity
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
        
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();
		List<Machine> machines = new ArrayList<>();
			
		machines = machineRepo.findAll();
		out.println("<tbody>");
		for (Machine machine : machines) {
			out.print("<tr>\r\n"
					+ "		<td>"+machine.getId()+"</td>\r\n"
					+ "		<td>"+machine.getCode()+"</td>\r\n"
					+ "		<td>"+machine.getName()+"</td>\r\n"
					+ "		<td>"+machine.getIpAddress()+"</td>\r\n"
					+ "		<td>"+machine.getPort()+"</td>\r\n"
					+ "		<td>\r\n"
					+ "			<button>Edit</button>\r\n"
					+ "			<button onclick='removeMachine("+machine.getId()+")'>Remove</button>\r\n"
					+ "		</td>\r\n"
					+ "</tr>");
		}	
		out.println("</tbody>");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
