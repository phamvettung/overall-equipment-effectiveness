package vn.intech.oee2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.intech.oee2025.entity.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {

}
