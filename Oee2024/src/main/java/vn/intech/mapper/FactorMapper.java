package vn.intech.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.intech.dto.FactorDto;
import vn.intech.entity.Factors;

@Service
public class FactorMapper {
	@Autowired
	private ModelMapper mapper; 
	
	public Factors convertToEntity(FactorDto factorDto) {
		Factors entity = mapper.map(factorDto, Factors.class);
		return entity;
	}
	
	public FactorDto convertToDto(Factors entity) {
		FactorDto dto = mapper.map(entity, FactorDto.class);
		return dto;
	}
}
