package com.alura.literalura.domain.port;

import com.alura.literalura.domain.model.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);
    List<Author> findAll();
    Optional<Author> findByName(String name);
    List<Author> findAuthorsAliveInYear(int year);
}
