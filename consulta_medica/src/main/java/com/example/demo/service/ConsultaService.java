package com.example.demo.service;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Paciente;
import com.example.demo.Enums.StatusConsulta;
import com.example.demo.dto.ConsultaRequestDTO;
import com.example.demo.dto.ConsultaResponseDTO;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.IConsultaRepository;
import com.example.demo.repository.IMedicoRepository;
import com.example.demo.repository.IPacienteRepository;

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

    public ConsultaResponseDTO agendarConsulta(ConsultaRequestDTO consultaDTO) {
        // Regra de horário comercial
        if (consultaDTO.getDataHora().getHour() < 8 || consultaDTO.getDataHora().getHour() > 18) {
            throw new IllegalArgumentException("O agendamento deve ser feito dentro do horário comercial (08h às 18h).");
        }

        // Regra de datas futuras
        if (consultaDTO.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar consultas para datas passadas.");
        }

        // Buscar entidades associadas
        Medico medico = medicoRepository.findById(consultaDTO.getIdMedico())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));

        Paciente paciente = pacienteRepository.findById(consultaDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));

        // Montar entidade
        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataHora(consultaDTO.getDataHora());
        consulta.setStatus(consultaDTO.getStatus() != null ? consultaDTO.getStatus() : StatusConsulta.AGENDADA);
        consulta.setObservacoes(consultaDTO.getObservacoes());

        // Salvar no banco
        Consulta consultaSalva = consultaRepository.save(consulta);

        // Retornar DTO de resposta
        return consultaMapper.toDTO(consultaSalva);
    }

    public List<ConsultaResponseDTO> listarConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultaMapper.toDTOList(consultas);
    }

    public Optional<ConsultaResponseDTO> buscarConsultaPorId(Long id) {
        return consultaRepository.findById(id).map(consultaMapper::toDTO);
    }

    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada."));

        if (consulta.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new IllegalArgumentException("Cancelamento permitido apenas com 24h de antecedência.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }
}
