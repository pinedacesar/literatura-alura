package com.aluracursos.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibrosPorAutorDTO(
        String titulo,
        String fechaDeNacimiento,
        String fechaDeFallecimiento
) {
}
