package vn.intech.oee2025.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.oee2025.dto.InputDto;
import vn.intech.oee2025.entity.Input;

@Service
public class InputMapper {
	@Autowired
	private ModelMapper mapper; 
	
	public Input convertToEntity(InputDto dto) {
		Input entity = mapper.map(dto, Input.class);
		return entity;
	}
	
	public InputDto convertToDto(Input entity) {
		InputDto dto = mapper.map(entity, InputDto.class);
		return dto;
	}
}
