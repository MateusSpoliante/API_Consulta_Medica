package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Consulta;
import com.example.demo.dto.ConsultaDTO;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    ConsultaDTO toDTO(Consulta consulta);

    Consulta toEntity(ConsultaDTO dto);

    List<ConsultaDTO> toDTOList(List<Consulta> consultas);
}
