package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.dto.DisponibilidadeDTO;

@Mapper(componentModel = "spring")
public interface DisponibilidadeMapper {

    DisponibilidadeDTO toDTO(Disponibilidade disponibilidade);

    Disponibilidade toEntity(DisponibilidadeDTO dto);

    List<DisponibilidadeDTO> toDTOList(List<Disponibilidade> disponibilidades);
}
