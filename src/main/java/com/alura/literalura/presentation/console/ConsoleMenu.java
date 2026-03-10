package com.alura.literalura.presentation.console;

import com.alura.literalura.application.usecase.AuthorService;
import com.alura.literalura.application.usecase.BookService;
import com.alura.literalura.domain.model.Author;
import com.alura.literalura.domain.model.Book;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private static final String SEPARATOR = "=".repeat(50);
    private final BookService bookService;
    private final AuthorService authorService;
    private final Scanner scanner;

    public ConsoleMenu(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println(SEPARATOR);
        System.out.println("   LiterAlura - Catalogo de Libros");
        System.out.println(SEPARATOR);

        boolean running = true;
        while (running) {
            printMenu();
            int option = readInt("Selecciona una opcion: ");
            running = handleOption(option);
        }
    }

    private void printMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("  1. Buscar libro por titulo");
        System.out.println("  2. Listar todos los libros");
        System.out.println("  3. Listar todos los autores");
        System.out.println("  4. Listar autores vivos en un año");
        System.out.println("  5. Listar libros por idioma");
        System.out.println("  0. Salir");
        System.out.println(SEPARATOR);
    }

    private boolean handleOption(int option) {
        return switch (option) {
            case 1 -> { searchBook(); yield true; }
            case 2 -> { listAllBooks(); yield true; }
            case 3 -> { listAllAuthors(); yield true; }
            case 4 -> { listAuthorsAliveInYear(); yield true; }
            case 5 -> { listBooksByLanguage(); yield true; }
            case 0 -> { System.out.println("\nHasta luego."); yield false; }
            default -> { System.out.println("Opcion no valida."); yield true; }
        };
    }

    private void searchBook() {
        System.out.print("Ingresa el titulo del libro: ");
        String title = scanner.nextLine().trim();
        try {
            Book book = bookService.searchAndSaveBook(title);
            System.out.println("\n[OK] Libro encontrado:");
            System.out.println("  " + book);
        } catch (RuntimeException e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private void listAllBooks() {
        List<Book> books = bookService.findAllBooks();
        if (books.isEmpty()) {
            System.out.println("\nNo hay libros registrados aun.");
            return;
        }
        System.out.println("\n--- Libros registrados ---");
        books.forEach(b -> System.out.println("  " + b));
    }

    private void listAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("\nNo hay autores registrados aun.");
            return;
        }
        System.out.println("\n--- Autores registrados ---");
        authors.forEach(a -> System.out.println("  " + a));
    }

    private void listAuthorsAliveInYear() {
        int year = readInt("Ingresa el año: ");
        List<Author> authors = authorService.findAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("\nNo se encontraron autores vivos en el año " + year);
            return;
        }
        System.out.println("\n--- Autores vivos en " + year + " ---");
        authors.forEach(a -> System.out.println("  " + a));
    }

    private void listBooksByLanguage() {
        System.out.println("\nIdiomas disponibles: en, es, fr, pt");
        System.out.print("Ingresa el codigo de idioma: ");
        String language = scanner.nextLine().trim().toLowerCase();
        List<Book> books = bookService.findBooksByLanguage(language);
        if (books.isEmpty()) {
            System.out.println("\nNo hay libros registrados en ese idioma.");
            return;
        }
        System.out.println("\n--- Libros en idioma: " + language + " ---");
        books.forEach(b -> System.out.println("  " + b));
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Ingresa un numero valido.");
            }
        }
    }
}
