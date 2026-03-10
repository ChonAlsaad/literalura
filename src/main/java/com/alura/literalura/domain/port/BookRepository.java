package com.alura.literalura.domain.port;

import com.alura.literalura.domain.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findByTitle(String title);
    List<Book> findByLanguage(String language);
    boolean existsByTitle(String title);
}
