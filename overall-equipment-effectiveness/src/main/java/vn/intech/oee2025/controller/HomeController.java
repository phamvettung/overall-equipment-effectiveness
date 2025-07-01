package vn.intech.oee2025.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.intech.oee2025.dto.MachineDataDto;
import vn.intech.oee2025.dto.OeeDto;
import vn.intech.oee2025.dto.OeeMdcDto;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.service.impl.OeeServiceImpl;

@Controller
@RequestMapping("home")
public class HomeController {
	
	@Autowired
	private MachineRepository machineRepo;	
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
	
		List<Machine> machines = machineRepo.findAll();
		LocalDate currentDate = LocalDate.now();
		Date startDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1));
		Date endDate = Date.valueOf(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()));
		List<OeeDto> oees= oeeService.getMonthlyOee(startDate, endDate);	
		
		List<OeeDto> oeeOfMonth = new ArrayList<>();
		for (Machine machine : machines) {
			Boolean isExist = false;
			for (OeeDto oeeDto : oees) {
				if(machine.getId() == oeeDto.getMachineId()) {
					isExist = true;
					oeeOfMonth.add(oeeDto);
					break;
				}
			}
			if(!isExist) {
				OeeDto newOee = new OeeDto();
				newOee.setMachineName(machine.getName());
				newOee.setMachineId(machine.getId());
				newOee.setA(0);
				newOee.setP(0);
				newOee.setQ(0);
				newOee.setOee(0);
				newOee.setRuntime(0);
				newOee.setDowntime(0);
				oeeOfMonth.add(newOee);
			}
		}
		
		
		List<MachineDataDto> mdc = oeeService.getMDC(startDate, endDate);
		List<OeeMdcDto> oeeMdc = new ArrayList<>();		
		for (Machine machine : machines) {
			Boolean isExist1 = false;
			for (OeeDto oeeDto : oees) {
				if(machine.getId() == oeeDto.getMachineId()) {
					isExist1 = true;				
					OeeMdcDto oeeMdcDto = new OeeMdcDto();
					oeeMdcDto.setMachineId(oeeDto.getMachineId());
					oeeMdcDto.setMachineName(oeeDto.getMachineName());
					oeeMdcDto.setRuntime(oeeDto.getRuntime());
					oeeMdcDto.setDowntime(oeeDto.getDowntime());
					oeeMdcDto.setA(oeeDto.getA());
					oeeMdcDto.setP(oeeDto.getP());
					oeeMdcDto.setQ(oeeDto.getQ());
					oeeMdcDto.setOee(oeeDto.getOee());
					
					MachineDataDto mdtDto = mdc.stream()
							.filter(item -> item.getMachineId() == oeeDto.getMachineId())
							.findFirst()
			                .orElse(null);
					if(mdtDto != null) {
						oeeMdcDto.setQ200(mdtDto.getQ200());
						oeeMdcDto.setQ201(mdtDto.getQ201());
						oeeMdcDto.setQ500(mdtDto.getQ500());
					}				
					oeeMdc.add(oeeMdcDto);
					break;
				}
			}
			if(!isExist1) {
				OeeMdcDto oeeMdcDto = new OeeMdcDto();
				oeeMdcDto.setMachineId(machine.getId());
				oeeMdcDto.setMachineName(machine.getName());
				oeeMdc.add(oeeMdcDto);
			}
		}
		

		
		System.out.println("LENGTH= " + oeeMdc.size());
								
		model.addAttribute("machines", machines);
		//model.addAttribute("oeeOfMonth", oeeOfMonth);
		model.addAttribute("oeeOfMonth", oeeMdc);
		return "home";
	}
	
}
