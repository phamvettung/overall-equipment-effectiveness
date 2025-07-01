package vn.intech.oee2025.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.MachineRepository;

@Service
public class MachineService {
	private final MachineRepository machineRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public MachineService(MachineRepository machineRepo) {
        this.machineRepo = machineRepo;
    }

    public List<Machine> getAllMachines() {
        return machineRepo.findAll();
    }

    @Transactional
    public void removeMachineById(Integer id) {
        // delete sub data first
        entityManager.createQuery("DELETE FROM Input i WHERE i.machine.id = :id")
            .setParameter("id", id)
            .executeUpdate();
        entityManager.createQuery("DELETE FROM MachineDataCollection mdc WHERE mdc.machine.id = :id")
            .setParameter("id", id)
            .executeUpdate();

        // delete machine
        entityManager.createQuery("DELETE FROM Machine m WHERE m.id = :id")
            .setParameter("id", id)
            .executeUpdate();
    }
}
