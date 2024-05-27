package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    @Query(value = "SELECT autor, " +
            "json_agg(json_build_object(" +
            "  'titulo', titulo, " +
            "  'fechaDeNacimiento', fecha_de_nacimiento, " +
            "  'fechaDeFallecimiento', fecha_de_fallecimiento " +
            ")) AS libros " +
            "FROM libros " +
            "GROUP BY autor", nativeQuery = true)
    List<Object[]> listasAutores();

    @Query(value = "SELECT autor, " +
            "json_agg(json_build_object(" +
            "  'titulo', titulo, " +
            "  'fechaDeNacimiento', fecha_de_nacimiento, " +
            "  'fechaDeFallecimiento', fecha_de_fallecimiento " +
            ")) AS libros " +
            "FROM libros " +
            "WHERE fecha_de_nacimiento::INTEGER <= CAST(:year AS INTEGER) " +
            "AND (fecha_de_fallecimiento::INTEGER >= CAST(:year AS INTEGER) OR fecha_de_fallecimiento IS NULL) " +
            "GROUP BY autor", nativeQuery = true)
    List<Object[]> findAutoresVivosEnAno(@Param("year") String year);

    List<Libro> findByIdioma(String idioma);

}
