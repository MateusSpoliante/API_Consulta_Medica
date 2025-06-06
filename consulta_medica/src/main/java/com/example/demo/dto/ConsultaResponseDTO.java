package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.Enums.StatusConsulta;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultaResponseDTO {

    private Long id;
    private PacienteResumoDTO paciente;
    private MedicoResumoDTO medico;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String observacoes;
}
