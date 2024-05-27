package com.aluracursos.literatura.model;

import com.aluracursos.literatura.dto.LibrosPorAutorDTO;

import java.util.List;

public class AutorConLibros {
    private String autor;
    private List<LibrosPorAutorDTO> libros;

    public AutorConLibros(String autor, List<LibrosPorAutorDTO> libros) {
        this.autor = autor;
        this.libros = libros;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<LibrosPorAutorDTO> getLibros() {
        return libros;
    }

    public void setLibros(List<LibrosPorAutorDTO> libros) {
        this.libros = libros;
    }
}
