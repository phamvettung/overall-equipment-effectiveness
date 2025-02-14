package vn.intech.controller;

import java.util.List;

import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.intech.dto.MachineDto;
import vn.intech.entity.Machines;
import vn.intech.mapper.MachineMapper;
import vn.intech.repositories.MachineRepository;

@Controller
@RequestMapping(value="machines")
public class MachineController {
	@Autowired
	private MachineRepository machineRepo;
	
	@Autowired
	private MachineMapper machineMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		Sort sortByMachineName = Sort.by(Direction.DESC, "name");
		List<Machines> lst = this.machineRepo.findAll(sortByMachineName);
		model.addAttribute("machineList", lst);
		return "manager/machine";
	}
	
	@GetMapping(value="{id}")
	public String show(Model model, @PathVariable("id") String id){
		Machines machineEntity =  this.machineRepo.getOne(id);	
		MachineDto machinesDto = this.machineMapper.convertToDto(machineEntity);	
		model.addAttribute("machine", machinesDto);
		return "manager/machine";
	}
	
	
	public String create(){
		return "";
	}
	
	@PostMapping(value="/store")
	public String store(Model model, MachineDto machine, BindingResult result){
		if(result.hasErrors()) {
			System.out.println("Có lỗi");
			model.addAttribute("error", result.getAllErrors());
			model.addAttribute("machine", machine);
			return "manager/create";
		}else {
			Machines entity = this.machineMapper.convertToEntity(machine);
			this.machineRepo.save(entity);
			return "redirect:/manager/machine";
		}
	}
	
	@GetMapping(value = "edit/{id}")
	public String edit( Model model, @PathVariable("id") Machines entity){
		MachineDto machinesDto = this.machineMapper.convertToDto(entity);	
		model.addAttribute("machine", machinesDto);
		return "manager/edit";
	}
	
	@PostMapping(value="upadte/{id}")
	public String update(Model model, MachineDto machine, BindingResult result){
		if(result.hasErrors()) {
			System.out.println("Có lỗi");
			model.addAttribute("error", result.getAllErrors());
			return "manager/edit";
		}else {
			Machines entity = this.machineMapper.convertToEntity(machine);
			this.machineRepo.save(entity);
			return "redirect:/manager/machine";
		}
	}
	
	@PostMapping(value="delete/{id}")
	public String delete(@PathVariable("id") String id){
		this.machineRepo.deleteById(id);
		return "redirect:/manager/machine";
	}
	
	@RequestMapping(value = "manager/test", method = RequestMethod.GET)
	public String testRedirect() {
		return "redirect:/home-page";
	}
}
