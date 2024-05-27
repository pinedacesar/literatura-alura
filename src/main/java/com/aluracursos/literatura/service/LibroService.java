package com.aluracursos.literatura.service;

import com.aluracursos.literatura.dto.LibroDTO;
import com.aluracursos.literatura.dto.LibrosPorAutorDTO;
import com.aluracursos.literatura.model.AutorConLibros;
import com.aluracursos.literatura.model.Libro;
import com.aluracursos.literatura.repository.LibroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<LibroDTO> obtenerTodosLibros(){
        List<Libro> libros = repository.findAll();
        return convierteDatos(libros);
    }

    public List<LibroDTO> convierteDatos(List<Libro> libro){
        return libro.stream()
                .map(l -> new LibroDTO(l.getTitulo(), l.getAutor(), l.getIdioma(), l.getNumeroDeDescargas()))
                .collect(Collectors.toList());
    }

    public List<AutorConLibros> listarAutores() {
        List<Object[]> results = repository.listasAutores();
        List<AutorConLibros> autoresConLibros = new ArrayList<>();

        for (Object[] result : results) {
            String autor = (String) result[0];
            String librosJson = (String) result[1];

            try {
                List<LibrosPorAutorDTO> libros = objectMapper.readValue(librosJson, new TypeReference<List<LibrosPorAutorDTO>>() {});
                autoresConLibros.add(new AutorConLibros(autor, libros));
            } catch (Exception e) {
                e.printStackTrace();
                // Manejo de errores
            }
        }

        return autoresConLibros;
    }

    public List<AutorConLibros> listarAutoresVivosEnAno(String year) {
        try {
            List<Object[]> results = repository.findAutoresVivosEnAno(year);
            List<AutorConLibros> autoresConLibros = new ArrayList<>();

            for (Object[] result : results) {
                String autor = (String) result[0];
                String librosJson = (String) result[1];
                List<LibrosPorAutorDTO> libros = objectMapper.readValue(librosJson, new TypeReference<List<LibrosPorAutorDTO>>() {});
                autoresConLibros.add(new AutorConLibros(autor, libros));
            }

            return autoresConLibros;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar autores vivos en el año " + year, e);
        }
    }

    public List<LibroDTO> listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = repository.findByIdioma(idioma);
        return convertirALibroDTO(libros);
    }

    private List<LibroDTO> convertirALibroDTO(List<Libro> libros) {
        return libros.stream()
                .map(libro -> new LibroDTO(
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIdioma(),
                        libro.getNumeroDeDescargas()
                ))
                .collect(Collectors.toList());
    }

}
