package com.alura.literalura.application.usecase;

import com.alura.literalura.domain.model.Author;
import com.alura.literalura.domain.model.Book;
import com.alura.literalura.domain.port.AuthorRepository;
import com.alura.literalura.domain.port.BookRepository;
import com.alura.literalura.infrastructure.dto.BookDto;
import com.alura.literalura.infrastructure.http.GutendexClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final GutendexClient gutendexClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(GutendexClient gutendexClient,
                       BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.gutendexClient = gutendexClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book searchAndSaveBook(String title) {
        if (bookRepository.existsByTitle(title)) {
            return bookRepository.findByTitle(title).orElseThrow();
        }

        var response = gutendexClient.searchByTitle(title);

        if (response.results().isEmpty()) {
            throw new RuntimeException("No se encontró ningún libro con el título: " + title);
        }

        BookDto bookDto = response.results().get(0);

        if (bookDto.authors().isEmpty()) {
            throw new RuntimeException("El libro no tiene autores registrados en la API");
        }

        var authorDto = bookDto.authors().get(0);
        Author author = authorRepository.findByName(authorDto.name())
            .orElseGet(() -> new Author(
                authorDto.name(),
                authorDto.birthYear(),
                authorDto.deathYear()
            ));

        String language = bookDto.languages().isEmpty() ? "unknown" : bookDto.languages().get(0);

        Book book = new Book(bookDto.title(), language, bookDto.downloadCount(), author);
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
}
