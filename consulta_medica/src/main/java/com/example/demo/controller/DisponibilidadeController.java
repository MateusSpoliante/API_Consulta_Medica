package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.service.DisponibilidadeService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Disponibilidades", description = "Endpoints para gerenciamento de disponibilidades de médicos")
@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @Operation(summary = "Listar disponibilidades de um médico", description = "Listar as disponibilidades de um médico por ID")
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<ApiResponse<List<DisponibilidadeDTO>>> listarDisponibilidades(@PathVariable Long medicoId) {
        List<DisponibilidadeDTO> disponibilidades = disponibilidadeService.listarDisponibilidades(medicoId);
        return ResponseEntity.ok(new ApiResponse<>(disponibilidades));
    }

    @Operation(summary = "Adicionar disponibilidade para um médico", description = "Adicionar um novo horário de disponibilidade para um médico")
    @PostMapping
    public ResponseEntity<ApiResponse<DisponibilidadeDTO>> adicionarDisponibilidade(@RequestBody DisponibilidadeDTO disponibilidadeDTO) {
        try {
            DisponibilidadeDTO novaDisponibilidade = disponibilidadeService.adicionarDisponibilidade(disponibilidadeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(novaDisponibilidade));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse("Erro ao adicionar disponibilidade", e.getMessage())));
        }
    }
}
