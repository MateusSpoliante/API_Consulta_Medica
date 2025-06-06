package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Consulta;
import com.example.demo.dto.ConsultaResponseDTO;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    ConsultaResponseDTO toDTO(Consulta consulta);

    Consulta toEntity(ConsultaResponseDTO dto);

    List<ConsultaResponseDTO> toDTOList(List<Consulta> consultas);
}
