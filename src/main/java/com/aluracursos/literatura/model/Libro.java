package com.aluracursos.literatura.model;

import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String autor;
    private String idioma;
    private Long numeroDeDescargas;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;

    public Libro(){}

    public Libro(DatosLibros datos){
        this.titulo = datos.titulos();
        this.numeroDeDescargas = datos.numeroDeDescargas();

        this.autor = datos.autor().stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));

        this.idioma = String.join(", ", datos.idiomas());

        this.fechaDeNacimiento = datos.autor().stream()
                .map(a -> String.valueOf(a.fechaDeNacimiento()))
                .collect(Collectors.joining(", "));

        this.fechaDeFallecimiento = datos.autor().stream()
                .map(a -> String.valueOf(a.fechaDeFallecimiento()))
                .collect(Collectors.joining(", "));
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Long numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }


    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeFallecimiento='" + fechaDeFallecimiento + '\'' +
                '}';
    }
}
