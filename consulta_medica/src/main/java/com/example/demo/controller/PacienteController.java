package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacienteDTO;
import com.example.demo.service.PacienteService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "pacientes", description = "Endpoints para gerenciamento de pacientes")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Operation(summary = "Lista todos os pacientes", description = "Retorna uma lista de todos os pacientes cadastrados")
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        List<PacienteDTO> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @Operation(summary = "Busca um paciente por ID", description = "Retorna um paciente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<PacienteDTO> pacienteDTO = pacienteService.buscarPorId(id);
        return pacienteDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo paciente", description = "Cadastra um novo paciente no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<PacienteDTO>> criarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {

        try {
            PacienteDTO savedPaciente = pacienteService.salvar(pacienteDTO);

            ApiResponse<PacienteDTO> response = new ApiResponse<>(savedPaciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {

            ErrorResponse errorResponse = new ErrorResponse("Argumento Inválido", e.getMessage());
            ApiResponse<PacienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro Interno", e.getMessage());
            ApiResponse<PacienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Deleta um paciente", description = "Remove um paciente do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPaciente(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza paciente", description = "Atualiza os dados de um paciente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteDTO>> atualizarPaciente(@PathVariable Long id,
            @Valid @RequestBody PacienteDTO pacienteDTO) {
        try {
            pacienteDTO.setId(id);

            PacienteDTO atualizado = pacienteService.atualizar(pacienteDTO);

            return ResponseEntity.ok(new ApiResponse<>(atualizado));

        } catch (IllegalArgumentException e) {

            ErrorResponse erro = new ErrorResponse("Erro de validação", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(erro));

        } catch (Exception e) {
            ErrorResponse erro = new ErrorResponse("Erro de validação", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(erro));
        }
    }
}
