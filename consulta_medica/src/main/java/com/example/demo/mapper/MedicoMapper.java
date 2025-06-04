package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Medico;
import com.example.demo.dto.MedicoRequestDTO;
import com.example.demo.dto.MedicoResponseDTO;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    Medico toEntity(MedicoRequestDTO dto);

    MedicoResponseDTO toDTO(Medico medico);

    List<MedicoResponseDTO> toDTOList(List<Medico> medicos);
}
