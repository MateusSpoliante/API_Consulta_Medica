package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Paciente;
import com.example.demo.dto.PacienteDTO;
import com.example.demo.mapper.PacienteMapper;
import com.example.demo.repository.IPacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    public List<PacienteDTO> listarTodos() {
        return pacienteMapper.toDTOList(pacienteRepository.findAll());
    }

    public Optional<PacienteDTO> buscarPorId(Long id) {
        return pacienteRepository.findById(id).map(pacienteMapper::toDto);
    }

    public PacienteDTO salvar(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        return pacienteMapper.toDto(pacienteRepository.save(paciente));
    }

    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

   public PacienteDTO atualizar(PacienteDTO pacienteDTO) {

    Paciente pacienteExistente = pacienteRepository.findById(pacienteDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

    pacienteExistente.setNome(pacienteDTO.getNome());
    pacienteExistente.setCpf(pacienteDTO.getCpf());
    pacienteExistente.setEmail(pacienteDTO.getEmail());
    pacienteExistente.setTelefone(pacienteDTO.getTelefone());

    Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
    return pacienteMapper.toDto(pacienteAtualizado);
   }

}
