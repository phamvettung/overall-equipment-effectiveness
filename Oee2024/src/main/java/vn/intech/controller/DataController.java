package vn.intech.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import vn.intech.dto.OeeDto;
import vn.intech.entity.Factors;
import vn.intech.entity.Inputs;
import vn.intech.entity.Machines;
import vn.intech.repositories.FactorRepository;
import vn.intech.repositories.InputRepository;
import vn.intech.repositories.MachineRepository;
import vn.intech.service.impl.OeeServiceImpl;

@Controller
@RequestMapping("data-page")
public class DataController {
	
	@Autowired
	MachineRepository machineRepo;
	@Autowired
	FactorRepository factorRepo;
	@Autowired
	InputRepository inputRepo;
	@Autowired
	HttpServletResponse response;
	@Autowired
	HttpServletRequest request;
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		
		List<Machines> lstMachine = machineRepo.findAll();
		Machines machineRemoved =  lstMachine.remove(8);
		
		List<Factors> lstFactor = factorRepo.findAll();
		
		model.addAttribute("lstMachine", lstMachine);
		model.addAttribute("lstFactor", lstFactor);
		
		request.setAttribute("namePage", "Cơ sở dữ liệu");
		
		return "database";
	}
	
	@PostMapping()
	public ResponseEntity<String> getOeeTable(
			@PathParam("mid") String mid, 
			@PathParam("start") Timestamp start, 
			@PathParam("end") Timestamp end
			) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		Date startDate = new Date(start.getTime());
		Date endDate = new Date(end.getTime());
		PrintWriter out = response.getWriter();	
		if(!mid.equals("all")) {			
			List<OeeDto> lstOee = this.oeeService.readOeeTable(mid,startDate, endDate);
			for (OeeDto oee : lstOee) {
				out.print("<tbody>\r\n"
						+ "		<tr onclick=\"trGetDateOnClick(this)\">\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getDate()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getName()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF0()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF00()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF1()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF10()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF2()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF3()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF4()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF5()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF6()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF7()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF8()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF9()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getDin()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getA()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getP()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getQ()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getOee()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getDownTime()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getRunTime()+"</td>\r\n"
						+ "		</tr>\r\n"
						+ "	</tbody>");
			}
		}else {
			List<OeeDto> lstOee = this.oeeService.readOeeTrend(startDate, endDate);
			for (OeeDto oee : lstOee) {
				out.print("<tbody>\r\n"
						+ "		<tr onclick=\"trGetDateOnClick(this)\">\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">None</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getName()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF0()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF00()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF1()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF10()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF2()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF3()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF4()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF5()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF6()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF7()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF8()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getF9()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getDin()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getA()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getP()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getQ()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getOee()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getDownTime()+"</td>\r\n"
						+ "			<td onclick=\"tdGetFactorIdOnClick(this)\" ondblclick=\"redirectToDetail()\">"+oee.getRunTime()+"</td>\r\n"
						+ "		</tr>\r\n"
						+ "	</tbody>");
			}
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/detail-form")
	public ResponseEntity<String> getInputTable(
			@PathParam("date") String date, 
			@PathParam("fid") String fid, 
			@PathParam("mid") String mid
			) throws IOException{	
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Inputs> lstInput = this.inputRepo.findByDateAndFidAndMid(Date.valueOf(date), fid, mid);
		for (Inputs input : lstInput) {
			out.print("<tbody>\r\n"
					+ "		<tr>\r\n"
					+ "			<td>"+input.getId()+"</td>\r\n"
					+ "			<td>"+input.getMachine().getName()+"</td>\r\n"
					+ "			<td>"+input.getDate()+"</td>\r\n"
					+ "			<td>"+input.getTime()+"</td>\r\n"
					+ "			<td>"+input.getFactor().getName()+"</td>\r\n"
					+ "			<td>"+input.getValue()+"</td>\r\n"
					+ "			<td><button class=\"btn btn-info\" onclick='deleteInputById("+input.getId()+")'>Xóa</button></td>\r\n"	
					+ "		</tr>\r\n"
					+ "</tbody>");
		}	
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@PostMapping(value="/delete-input/{id}")
	public ResponseEntity<String> deleteInputById(
			@PathVariable("id") Inputs entity
			){
		inputRepo.delete(entity);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
