package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.Enums.StatusConsulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultaRequestDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long idPaciente;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long idMedico;

    @NotNull(message = "A data e hora da consulta são obrigatórias")
    @Future(message = "A data da consulta deve ser no futuro")
    private LocalDateTime dataHora;

    @NotNull(message = "O status da consulta é obrigatório")
    private StatusConsulta status;

    private String observacoes;
}
