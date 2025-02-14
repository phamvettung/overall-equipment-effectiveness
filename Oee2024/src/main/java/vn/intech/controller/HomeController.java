package vn.intech.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import vn.intech.entity.Machines;
import vn.intech.mapper.MachineMapper;
import vn.intech.repositories.InputRepository;
import vn.intech.repositories.MachineRepository;
import vn.intech.service.impl.OeeServiceImpl;
import vn.intech.dto.*;

@Controller
@RequestMapping("home-page")
public class HomeController {
	
	@Autowired
	private MachineRepository machineRepo;
	
	@Autowired
	private MachineMapper machineMapper;
	
	@Autowired
	private InputRepository inputRepo;
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		Sort sortByMachineName = Sort.by(Direction.ASC, "id");
		List<Machines> lstMachine = this.machineRepo.findAll(sortByMachineName);
		Machines machineRemove =  lstMachine.remove(8);
				
		model.addAttribute("listMachine", lstMachine);	
	
		LocalDate currentDate = LocalDate.now();
		Date startDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1));
		Date endDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
		List<OeeDto> lstOeeOfMonth = this.oeeService.readOeeTrend(startDate, endDate);
		model.addAttribute("lstOeeOfMonth", lstOeeOfMonth);
		
		request.setAttribute("namePage", "Trang chủ");
		
		return "home";
	}
}
