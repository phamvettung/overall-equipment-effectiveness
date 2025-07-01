package vn.intech.oee2025.controller.rest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.MachineRepository;

@RestController
@RequestMapping("api/machine")
public class MachineRestController {
	@Autowired
	private MachineRepository repo;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		System.out.println(1);
		List<Machine> machines = repo.findAll();
		if(machines.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(machines);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Machine machine){
		System.out.println(1);
		Machine createdMachine = repo.save(machine);
		URI uri = URI.create("/api/machine/" + createdMachine.getId());
		return ResponseEntity.created(uri).body(createdMachine);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer machineId, @RequestBody Machine machine){
		if(repo.findById(machineId).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<Machine> optionalMachine = repo.findById(machineId);
		Machine updatedMachine = null;
        if (optionalMachine.isPresent()) {
            Machine m = optionalMachine.get();
            m.setCode(machine.getCode());
            m.setName(machine.getName());
            m.setIpAddress(machine.getIpAddress());
            m.setPort(machine.getPort());
            updatedMachine = repo.save(m); // saves the updated entity
        } else {
            throw new RuntimeException("Machine not found with id: " + machineId);
        }            
        return ResponseEntity.ok(updatedMachine);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Machine machine){
		repo.delete(machine);
		return ResponseEntity.noContent().build();
	}
}
