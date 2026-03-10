# LiterAlura - Catálogo de Libros

Aplicación de consola desarrollada con Spring Boot que permite buscar, persistir y consultar libros y autores utilizando la API pública de Gutendex y una base de datos PostgreSQL.

## Tecnologías

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL 17
- Jackson
- Gutendex API (sin API key)

## Arquitectura

El proyecto aplica **Ports & Adapters** con separación estricta por capas:

```
src/main/java/com/alura/literalura/
├── domain/
│   ├── model/          # Author, Book (@Entity)
│   └── port/           # AuthorRepository, BookRepository (interfaces)
├── application/
│   └── usecase/        # AuthorService, BookService
├── infrastructure/
│   ├── dto/            # AuthorDto, BookDto, GutendexResponse (Jackson records)
│   ├── http/           # GutendexClient (HttpClient)
│   └── persistence/    # JpaAuthorRepository, JpaBookRepository
└── presentation/
    └── console/        # ConsoleMenu
```

La regla de dependencia es unidireccional: `presentation` → `application` → `domain` ← `infrastructure`. Las capas internas no conocen las externas.

## Funcionalidades

| # | Opción | Descripción |
|---|--------|-------------|
| 1 | Buscar libro por título | Consulta la API de Gutendex y persiste el resultado |
| 2 | Listar todos los libros | Muestra los libros guardados en la base de datos |
| 3 | Listar todos los autores | Muestra los autores registrados |
| 4 | Autores vivos en un año | Consulta autores vivos en el año ingresado |
| 5 | Libros por idioma | Filtra libros por código de idioma (en, es, fr, pt) |

## Modelo de datos

```
Author                    Book
------                    ----
id (PK)         <---      id (PK)
name (unique)   1    N    title (unique)
birthYear                 language
deathYear                 downloadCount
                          author_id (FK)
```

## Configuración

1. Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE literalura;
```

2. Configurar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.main.web-application-type=none
```

> ⚠️ `application.properties` está en `.gitignore` — nunca se commitea.

## Ejecución

```bash
# Windows (fix encoding UTF-8)
chcp 65001
mvn spring-boot:run
```

## Ejemplo de uso

```
==================================================
   LiterAlura - Catalogo de Libros
==================================================

==================================================
  1. Buscar libro por titulo
  2. Listar todos los libros
  3. Listar todos los autores
  4. Listar autores vivos en un año
  5. Listar libros por idioma
  0. Salir
==================================================
Selecciona una opcion: 1
Ingresa el titulo del libro: Pride and Prejudice

[OK] Libro encontrado:
  Titulo: Pride and Prejudice | Autor: Austen, Jane | Idioma: en | Descargas: 48793
```
