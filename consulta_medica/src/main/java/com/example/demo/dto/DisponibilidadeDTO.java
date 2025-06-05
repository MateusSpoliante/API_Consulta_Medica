package com.example.demo.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class DisponibilidadeDTO {

    private Long id;
    private Long medicoId;
    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
