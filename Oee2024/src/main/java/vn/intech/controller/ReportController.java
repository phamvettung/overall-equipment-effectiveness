package vn.intech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ReportController {
	@RequestMapping("reporting-page")
	public String index() {
		return "report";
	}
}
