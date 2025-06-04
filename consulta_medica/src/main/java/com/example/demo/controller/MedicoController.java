package com.example.demo.controller;

import com.example.demo.dto.MedicoRequestDTO;
import com.example.demo.dto.MedicoResponseDTO;
import com.example.demo.service.MedicoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Médicos", description = "Endpoints para gerenciamento de médicos especifíco")
@RestController
@RequestMapping("api/medicos")
public class MedicoController {
    @Autowired
    private MedicoService MedicoService;

    @Operation(summary = "Lista todos os médicos", description = "Retorna uma lista com todos os médicos cadastrados")
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarMedicos(){
        List<MedicoResponseDTO> medicos = MedicoService.listarTodos();
        return ResponseEntity.ok(medicos);
    }

    
    @Operation(summary = "Busca um médico por ID", description = "Retorna os detalhes de um médico específico")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id){
        Optional<MedicoResponseDTO> medicoDTO = MedicoService.buscarPorId(id);
        return medicoDTO.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastra um novo médico", description = "Cria um novo médico")
    @PostMapping
    public ResponseEntity<ApiResponse<MedicoResponseDTO>> criarMedico(@Valid @RequestBody MedicoRequestDTO dto){
        try{
            MedicoResponseDTO salvo = MedicoService.salvar(dto);
            ApiResponse<MedicoResponseDTO> response = new ApiResponse<>(salvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e){
            ErrorResponse error = new ErrorResponse("Argumento inválido", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(error));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Error interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(error));
        }
    }

    @Operation(summary = "Atualiza um médico", description = "Atualiza os dados de um médico pelo ID")
@PutMapping("/{id}")
public ResponseEntity<ApiResponse<MedicoResponseDTO>> atualizarMedico(
        @PathVariable Long id,
        @Valid @RequestBody MedicoRequestDTO dto) {

    try {
        MedicoResponseDTO atualizado = MedicoService.atualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(atualizado));
    } catch (IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse("Argumento inválido", e.getMessage());
        return ResponseEntity.badRequest().body(new ApiResponse<>(error));
    } catch (RuntimeException e) {
        ErrorResponse error = new ErrorResponse("Recurso não encontrado", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(error));
    } catch (Exception e) {
        ErrorResponse error = new ErrorResponse("Erro interno", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(error));
    }
}

    @Operation(summary = "Deleta um médico", description = "Remove um médico do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id){
        MedicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
