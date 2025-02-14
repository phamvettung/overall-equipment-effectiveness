package vn.intech.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.intech.entity.Machines;

public interface MachineRepository extends JpaRepository<Machines, String>{
	
}
