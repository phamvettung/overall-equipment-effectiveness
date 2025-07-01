package vn.intech.oee2025.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.oee2025.dto.MachineDto;
import vn.intech.oee2025.entity.Machine;

@Service
public class MachineMapper {
	@Autowired
	private ModelMapper mapper; 
	
	public Machine convertToEntity(MachineDto dto) {
		Machine entity = mapper.map(dto, Machine.class);
		return entity;
	}
	
	public MachineDto convertToDto(Machine entity) {
		MachineDto dto = mapper.map(entity, MachineDto.class);
		return dto;
	}
}
