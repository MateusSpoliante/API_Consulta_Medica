package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ConsultaRequestDTO;
import com.example.demo.dto.ConsultaResponseDTO;
import com.example.demo.service.ConsultaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Consultas", description = "Endpoints para gerenciamento de consultas")
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Agendar uma consulta", description = "Agendar uma nova consulta para um paciente com um médico específico")
    @PostMapping
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> agendarConsulta(
            @Valid @RequestBody ConsultaRequestDTO consultaDTO) {
        try {
            ConsultaResponseDTO consultaAgendada = consultaService.agendarConsulta(consultaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(consultaAgendada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse("Argumento inválido", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(new ErrorResponse("Erro interno", e.getMessage())));
        }
    }

    @Operation(summary = "Buscar consulta por ID", description = "Retorna os detalhes de uma consulta pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarConsultaPorId(@PathVariable Long id) {
        Optional<ConsultaResponseDTO> consultaDTO = consultaService.buscarConsultaPorId(id);
        return consultaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cancelar consulta", description = "Cancelar uma consulta já agendada")
    @PutMapping("cancelar/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        try {
            consultaService.cancelarConsulta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
