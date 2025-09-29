package vn.intech.oee2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.intech.oee2025.entity.MachineDataCollection;

@Repository
public interface MdcRepository extends JpaRepository<MachineDataCollection, Long>{

}
