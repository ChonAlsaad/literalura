package com.alura.literalura.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(
    @JsonAlias("count") int count,
    @JsonAlias("results") List<BookDto> results
) {}
