package vn.intech.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.dto.MachineDto;
import vn.intech.entity.Machines;

@Service
public class MachineMapper {
	@Autowired
	private ModelMapper mapper; 
	
	public Machines convertToEntity(MachineDto machineDto) {
		Machines entity = mapper.map(machineDto, Machines.class);
		return entity;
	}
	
	public MachineDto convertToDto(Machines entity) {
		MachineDto dto = mapper.map(entity, MachineDto.class);
		return dto;
	}
}
