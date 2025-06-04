package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicoResponseDTO {

    private Long id;

    private String nome;

    private String crm;

    private EspecialidadeDTO especialidade;
}
