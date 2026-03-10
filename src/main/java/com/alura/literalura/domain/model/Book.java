package com.alura.literalura.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String language;
    private Double downloadCount;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {}

    public Book(String title, String language, Double downloadCount, Author author) {
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
        this.author = author;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public Double getDownloadCount() { return downloadCount; }
    public Author getAuthor() { return author; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLanguage(String language) { this.language = language; }
    public void setDownloadCount(Double downloadCount) { this.downloadCount = downloadCount; }
    public void setAuthor(Author author) { this.author = author; }

    @Override
    public String toString() {
        return String.format(
            "Titulo: %s | Autor: %s | Idioma: %s | Descargas: %.0f",
            title,
            author != null ? author.getName() : "Desconocido",
            language,
            downloadCount
        );
    }
}
