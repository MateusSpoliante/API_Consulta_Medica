package com.example.demo.service;

import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.mapper.DisponibilidadeMapper;
import com.example.demo.repository.IDisponibilidadeRepository;
import com.example.demo.repository.IMedicoRepository;
import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadeService {

    @Autowired
    private IDisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private DisponibilidadeMapper disponibilidadeMapper;

    public List<DisponibilidadeDTO> listarDisponibilidades(Long medicoId) {
        return disponibilidadeMapper.toDTOList(disponibilidadeRepository.findByMedicoId(medicoId));
    }

    public DisponibilidadeDTO adicionarDisponibilidade(DisponibilidadeDTO disponibilidadeDTO) {
        Medico medico = medicoRepository.findById(disponibilidadeDTO.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));

        Disponibilidade disponibilidade = disponibilidadeMapper.toEntity(disponibilidadeDTO);
        disponibilidade.setMedico(medico);

        disponibilidade = disponibilidadeRepository.save(disponibilidade);
        return disponibilidadeMapper.toDTO(disponibilidade);
    }
}
