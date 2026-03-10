package com.alura.literalura.infrastructure.http;

import com.alura.literalura.infrastructure.dto.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public GutendexResponse searchByTitle(String title) {
        String url = BASE_URL + "?search=" + title.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        try {
            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
            );
            return objectMapper.readValue(response.body(), GutendexResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API de Gutendex", e);
        }
    }
}
