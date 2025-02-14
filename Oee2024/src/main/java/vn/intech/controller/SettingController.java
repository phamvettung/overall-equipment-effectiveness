package vn.intech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingController {
	@RequestMapping("setting-page")
	public String index() {
		return "setting";
	}
}
