package vn.intech.oee2025.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import vn.intech.oee2025.dto.MachineDataDto;
import vn.intech.oee2025.dto.OeeDto;
import vn.intech.oee2025.dto.OeeMdcDto;
import vn.intech.oee2025.entity.Downtime;
import vn.intech.oee2025.entity.Input;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.entity.QCommand;
import vn.intech.oee2025.repository.DowntimeRepository;
import vn.intech.oee2025.repository.InputRepository;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.repository.QCommandRepository;
import vn.intech.oee2025.service.impl.OeeServiceImpl;

@Controller
@RequestMapping("data")
public class DataController {

	@Autowired
	private MachineRepository machineRepo;
	@Autowired
	private DowntimeRepository downtimeRepo;
	@Autowired
	private InputRepository inputRepo;
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private QCommandRepository qCommandRepo;
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		List<Machine> machines = machineRepo.findAll();
		List<Downtime> downtimes = downtimeRepo.findAll();
		
		LocalDate currentDate = LocalDate.now();
		Date startDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1));
		Date endDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
		List<OeeDto> oees = oeeService.getMonthlyOee(startDate, endDate);
					
		model.addAttribute("machines", machines);
		model.addAttribute("downtimes", downtimes);
		model.addAttribute("oees", oees);
				
		return "data";
	}
	
	@PostMapping()
	public ResponseEntity<String> getDailyOee(
			@PathParam("mid") String mid, 
			@PathParam("start") Timestamp start, 
			@PathParam("end") Timestamp end
			) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		Date startDate = new Date(start.getTime());
		Date endDate = new Date(end.getTime());
		PrintWriter out = response.getWriter();	
		List<OeeDto> oees = null;
		if(!mid.equals("all")) {
			oees = this.oeeService.getDailyOee(mid, startDate, endDate);
			for (OeeDto oee : oees) {
				out.print("<tbody>\r\n"
						+ "		        		<tr onclick=\"getDateOnClick(this)\">\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getDate() +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getMachineName() +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF0()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF00()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF1()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF2()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF3()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF4()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF5()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF6()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF7()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF8()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF9()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF10()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getDin() +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getDowntime()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getRuntime()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getA()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getP()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getQ()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getOee()*100.0)/100.0 +"</td>\r\n"
						+ "		        		</tr>\r\n"
						+ "	</tbody>");
			}
		}else {
			oees = this.oeeService.getMonthlyOee(startDate, endDate);
			for (OeeDto oee : oees) {
				out.print("<tbody>\r\n"
						+ "		        		<tr onclick=\"getDateOnClick(this)\">\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getDate() +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getMachineName() +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF0()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF00()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF1()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF2()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF3()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF4()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF5()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF6()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF7()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF8()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF9()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getF10()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ oee.getDin()  +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getDowntime()*100.0)/100.0  +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getRuntime()*100.0)/100.0  +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getA()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getP()*100.0)/100.0  +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getQ()*100.0)/100.0 +"</td>\r\n"
						+ "		        			<td onclick=\"getDowntimeTypeOnClick(this)\" ondblclick=\"showDetailOnDblClick()\">"+ Math.round(oee.getOee()*100.0)/100.0 +"</td>\r\n"
						+ "		        		</tr>\r\n"
						+ "	</tbody>");
			}
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/detail-form")
	public ResponseEntity<String> getInputs(
			@PathParam("date") String date, //Path param name is same as argument name 
			@PathParam("type") String type, 
			@PathParam("mid") int mid
			) throws IOException{	
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();
		List<Input> inputs = this.inputRepo.findByDateAndTypeAndMid(Date.valueOf(date), type, mid);	
		for (Input input : inputs) {
			out.print("<tbody>\r\n"
					+ "		<tr>\r\n"
					+ "			<td>"+input.getId()+"</td>\r\n"
					+ "			<td>"+input.getMachine().getName()+"</td>\r\n"
					+ "			<td>"+input.getDate()+"</td>\r\n"
					+ "			<td>"+input.getTime()+"</td>\r\n"
					+ "			<td>"+input.getDownTime().getName()+"</td>\r\n"
					+ "			<td>"+input.getValue()+"</td>\r\n"
					+ "			<td><button class=\"btn btn-info\" onclick='removeInput("+input.getId()+")'>remove</button></td>\r\n"	
					+ "		</tr>\r\n"
					+ "</tbody>");
		}	
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/remove-input/{id}")
	public ResponseEntity<String> deleteInputById(
			@PathVariable("id") Input entity
			){
		inputRepo.delete(entity);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/mdc", method = RequestMethod.GET)
	public String mdcIndex(Model model) {
		List<QCommand> qCommands = qCommandRepo.findAll();
		
		LocalDate currentDate = LocalDate.now();
		Date startDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1));
		Date endDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
		List<MachineDataDto> mdc = oeeService.getMDC(startDate, endDate);
		
		model.addAttribute("qCommands", qCommands);
		model.addAttribute("mdc", mdc);
		
		return "mdc";
	}
	
}
