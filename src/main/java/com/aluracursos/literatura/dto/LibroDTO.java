package com.aluracursos.literatura.dto;

public record LibroDTO(
        String titulo,
        String autor,
        String idioma,
        Long numeroDeDescargas
) {
}
