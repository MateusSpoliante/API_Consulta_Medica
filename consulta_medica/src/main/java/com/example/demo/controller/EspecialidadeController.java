package com.example.demo.controller;

import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.service.EspecialidadeService;
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

@Tag(name = "Especialidades", description = "Endpoints para gerenciamento de especialidades médicas")
@RestController
@RequestMapping("api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @Operation(summary = "Lista todas as especialidades", description = "Retorna uma lista com todas as especialidades")
    @GetMapping
    public ResponseEntity<List<EspecialidadeDTO>> listarEspecialidades() {
        return ResponseEntity.ok(especialidadeService.listarTodos());
    }

    @Operation(summary = "Busca uma especialidade por ID", description = "Retorna os detalhes de uma especialidade específica")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDTO> buscarPorId(@PathVariable Long id) {
        Optional<EspecialidadeDTO> dto = especialidadeService.buscarPorId(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastra uma nova especialidade", description = "Cria uma nova especialidade no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<EspecialidadeDTO>> criarEspecialidade(@Valid @RequestBody EspecialidadeDTO dto) {
        try {
            EspecialidadeDTO salva = especialidadeService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(salva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse("Argumento inválido", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(new ErrorResponse("Erro interno", e.getMessage())));
        }
    }

    @Operation(summary = "Atualiza uma especialidade", description = "Atualiza os dados de uma especialidade pelo ID")
@PutMapping("/{id}")
public ResponseEntity<ApiResponse<EspecialidadeDTO>> atualizarEspecialidade(
        @PathVariable Long id,
        @Valid @RequestBody EspecialidadeDTO dto) {
    try {
        EspecialidadeDTO atualizada = especialidadeService.atualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(atualizada));
    } catch (RuntimeException e) {
        ErrorResponse error = new ErrorResponse("Recurso não encontrado", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(error));
    } catch (Exception e) {
        ErrorResponse error = new ErrorResponse("Erro interno", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(error));
    }
}

    @Operation(summary = "Deleta uma especialidade", description = "Remove uma especialidade do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
