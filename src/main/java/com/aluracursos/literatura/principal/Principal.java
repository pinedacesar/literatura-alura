package com.aluracursos.literatura.principal;

import com.aluracursos.literatura.dto.LibroDTO;
import com.aluracursos.literatura.dto.LibrosPorAutorDTO;
import com.aluracursos.literatura.model.AutorConLibros;
import com.aluracursos.literatura.model.Datos;
import com.aluracursos.literatura.model.DatosLibros;
import com.aluracursos.literatura.model.Libro;
import com.aluracursos.literatura.repository.LibroRepository;
import com.aluracursos.literatura.service.ConsumoAPI;
import com.aluracursos.literatura.service.ConvierteDatos;
import com.aluracursos.literatura.service.LibroPresente;
import com.aluracursos.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;
    private LibroPresente libroPresente;

    @Autowired
    private LibroService libroService;


    public Principal(LibroRepository repository) {
        this.repository = repository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    _____________________________________
                    
                    Elija la opción a traves de su número:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    obtenerTodosLibrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosEnAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibros getDatosLibro() {
        System.out.println("Escribe el autor del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulos().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
            return libroBuscado.get();
        } else {
            System.out.println("Libro no encontrado");
        }
        return null;
    }

    private void buscarLibroWeb() {
        DatosLibros datos = getDatosLibro();
        if (datos != null) {
            String titulo = datos.titulos();
            Optional<Libro> libroExistente = repository.findByTitulo(titulo);

            if (libroExistente.isPresent()) {
                Libro libro = libroExistente.get();
                LibroDTO libroDTO = new LibroDTO(
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIdioma(),
                        libro.getNumeroDeDescargas()
                );
                libroPresente.imprimirLibro(libroDTO);
            } else {
                Libro libro = new Libro(datos);
                repository.save(libro);
                LibroDTO libroDTO = new LibroDTO(
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIdioma(),
                        libro.getNumeroDeDescargas()
                );
                libroPresente.imprimirLibro(libroDTO);
            }
        } else {
            System.out.println("No se pudo guardar el libro porque no se encontró.");
        }
    }

    private void obtenerTodosLibrosRegistrados() {
        List<Libro> libros = repository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            // Convierte la lista de Libro a LibroDTO
            List<LibroDTO> librosDTO = libros.stream()
                    .map(libro -> new LibroDTO(
                            libro.getTitulo(),
                            libro.getAutor(),
                            libro.getIdioma(),
                            libro.getNumeroDeDescargas()))
                    .collect(Collectors.toList());

            // Imprime cada libro usando LibroDTO
            for (LibroDTO libroDTO : librosDTO) {
                libroPresente.imprimirLibro(libroDTO);
            }
        }
    }

    private void listarAutores() {
        List<AutorConLibros> autores = libroService.listarAutores();
        for (AutorConLibros autor : autores) {
            System.out.println("Autor: " + autor.getAutor());
            if (!autor.getLibros().isEmpty()) {
                LibrosPorAutorDTO primerLibro = autor.getLibros().get(0);
                System.out.println("Fecha de nacimiento: " + primerLibro.fechaDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + primerLibro.fechaDeFallecimiento());
            }
            System.out.println("Libros: ");
            for (LibrosPorAutorDTO libro : autor.getLibros()) {
                System.out.println(" - " + libro.titulo());
            }
            System.out.println("\n");
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.println("Ingrese el año:");
        String year = teclado.nextLine();

        try {
            List<AutorConLibros> autores = libroService.listarAutoresVivosEnAno(year);
            for (AutorConLibros autor : autores) {
                System.out.println("_____________________________________");
                System.out.println("Autor: " + autor.getAutor());
                if (!autor.getLibros().isEmpty()) {
                    LibrosPorAutorDTO primerLibro = autor.getLibros().get(0);
                    System.out.println("Fecha de nacimiento: " + primerLibro.fechaDeNacimiento());
                    System.out.println("Fecha de fallecimiento: " + primerLibro.fechaDeFallecimiento());
                }
                System.out.println("Libros: ");
                for (LibrosPorAutorDTO libro : autor.getLibros()) {
                    System.out.println(" - " + libro.titulo());
                }
                System.out.println("_____________________________________\n");
            }
        } catch (Exception e) {
            System.out.println("Error al listar autores vivos en el año " + year);
            e.printStackTrace();
        }
    }

    public void mostrarLibrosPorIdioma() {
        try {
            System.out.println("Seleccione un idioma:");
            System.out.println("1 - Español");
            System.out.println("2 - Inglés");
            System.out.println("3 - Francés");
            System.out.println("4 - Portugués");
            int opcion = teclado.nextInt();
            teclado.nextLine(); // Consume la nueva línea

            String idioma;
            switch (opcion) {
                case 1:
                    idioma = "es";
                    break;
                case 2:
                    idioma = "en";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 4:
                    idioma = "pt";
                    break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            List<LibroDTO> libros = libroService.listarLibrosPorIdioma(idioma);
            if (libros.isEmpty()) {
                System.out.println("No hay libros en ese idioma.");
            } else {
                for (LibroDTO libro : libros) {
                    libroPresente.imprimirLibro(libro);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            teclado.nextLine(); // Limpiar el búfer del teclado
        }
    }

}