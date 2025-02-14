package vn.intech.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.dto.InputDto;
import vn.intech.entity.Inputs;

@Service
public class InputMapper {
	@Autowired
	private ModelMapper mapper; 
	
	public Inputs convertToEntity(InputDto inputDto) {
		Inputs entity = mapper.map(inputDto, Inputs.class);
		return entity;
	}
	
	public InputDto convertToDto(Inputs entity) {
		InputDto dto = mapper.map(entity, InputDto.class);
		return dto;
	}
}
