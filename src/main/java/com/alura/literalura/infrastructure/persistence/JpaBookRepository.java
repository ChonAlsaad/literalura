package com.alura.literalura.infrastructure.persistence;

import com.alura.literalura.domain.model.Book;
import com.alura.literalura.domain.port.BookRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaBookRepository extends JpaRepository<Book, Long>, BookRepository {

    Optional<Book> findByTitle(String title);
    List<Book> findByLanguage(String language);
    boolean existsByTitle(String title);
}
