package vn.intech.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.intech.dto.OeeDto;
import vn.intech.service.impl.OeeServiceImpl;

@Controller
@RequestMapping(value="oee")
public class OeeController {

	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		List<OeeDto> lst = this.oeeService.readOeeTable("CNC1", Date.valueOf(LocalDate.of(2024, 2, 1)), Date.valueOf(LocalDate.of(2024, 5, 26)));
		return "";
	}
}
