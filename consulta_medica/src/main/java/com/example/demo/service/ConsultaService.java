package com.example.demo.service;

import com.example.demo.dto.ConsultaDTO;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.IConsultaRepository;
import com.example.demo.repository.IMedicoRepository;
import com.example.demo.repository.IPacienteRepository;
import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Paciente;
import com.example.demo.Enums.StatusConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    public ConsultaDTO agendarConsulta(ConsultaDTO consultaDTO) {
        if (consultaDTO.getDataHora().getHour() < 8 || consultaDTO.getDataHora().getHour() > 18) {
            throw new IllegalArgumentException("O agendamento deve ser feito dentro do horário comercial.");
        }

        if (consultaDTO.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar consultas para datas passadas.");
        }

        Medico medico = medicoRepository.findById(consultaDTO.getMedico().getId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));
        
        
        Paciente paciente = pacienteRepository.findById(consultaDTO.getPaciente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));

        Consulta consulta = consultaMapper.toEntity(consultaDTO);
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setStatus(StatusConsulta.AGENDADA);

        consulta = consultaRepository.save(consulta);
        return consultaMapper.toDTO(consulta);
    }

    public List<ConsultaDTO> listarConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultaMapper.toDTOList(consultas);
    }

    public Optional<ConsultaDTO> buscarConsultaPorId(Long id) {
        return consultaRepository.findById(id).map(consultaMapper::toDTO);
    }

    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada."));
        
        if (consulta.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new IllegalArgumentException("Cancelamento somente até 24h antes da consulta.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }
}
