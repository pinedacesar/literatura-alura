<p align="center">
  <img src="img/badge-literalura.png" alt="Insignia de Alura Latam">
</p>

# Desafío LiterAlura
Construir tu propio catálogo de libros: el **LiterAlura.**

## Descripción
Desarrollar un Catálogo de Libros que ofrezca interacción textual (vía consola) con los usuarios, proporcionando al menos 5 opciones de interacción. Los libros se buscarán a través de una API específica. La información sobre la API y las opciones de interacción con el usuario se detallará en la columna "Backlog"/"Listo para iniciar".

## Funcionalidades
- Interfaz de usuario simple y amigable.
- Uso de la API de  [Gutendex](https://gutendex.com/) la misma cuenta con un catálogo de información de más de 70.000 libros presentes en [Project Gutenberg](https://www.gutenberg.org/) (biblioteca en línea y gratuita).para obtener el listado de librostasas de cambio actualizadas.
- Manejo de excepciones para garantizar la robustez del programa.

### Opciones del Menú

1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma

## Instrucciones de Uso

1. Clona este repositorio en tu máquina local utilizando el comando `git clone`.
    ```bash
    git clone <URL_del_repositorio>
    ```
2. Abre el proyecto en tu IDE de Java preferido.
3. Actualiza las variables de entorno para la base de datos de PostgreSQL en el archivo de configuración correspondiente.
4. Ejecuta la clase `LiteraturaApplication` para iniciar el programa.
5. Sigue las instrucciones en pantalla para utilizar las distintas funcionalidades del programa.
6. Para salir del programa, elige la opción correspondiente en el menú o cierra la ventana de ejecución en tu IDE.

## Tecnologías Utilizadas

- [Spring Boot](https://spring.io/projects/spring-boot): Framework de desarrollo de aplicaciones Java.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Biblioteca de persistencia de datos para aplicaciones Spring.
- [PostgreSQL](https://www.postgresql.org/): Sistema de gestión de bases de datos relacional.
- [Jackson](https://github.com/FasterXML/jackson): Biblioteca de procesamiento JSON para Java.
- [Java 17](https://adoptium.net/es/temurin/releases/?version=17): Versión del lenguaje de programación utilizada en el desarrollo del proyecto.

# Autor

Este proyecto fue creado por **César Pineda**.