package com.aluracursos.literatura.service;

import com.aluracursos.literatura.dto.LibroDTO;

public class LibroPresente {
    public static void imprimirLibro(LibroDTO libro) {
        System.out.println("__________TITULO__________");
        System.out.println("Titulo: " + libro.titulo());
        System.out.println("Autor: " + libro.autor());
        System.out.println("Idioma: " + libro.idioma());
        System.out.println("Numero de descarga: " + libro.numeroDeDescargas());
        System.out.println("_________________________");
        System.out.println("");
    }
}
