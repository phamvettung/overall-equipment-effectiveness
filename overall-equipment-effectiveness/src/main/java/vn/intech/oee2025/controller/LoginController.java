package vn.intech.oee2025.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "login")
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String loginForm() {
		return "security/login";
	}	
}
