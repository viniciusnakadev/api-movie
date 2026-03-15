package br.com.vgn.apimovie.integration;

import br.com.vgn.apimovie.domain.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("You must load CSV records when the application starts.")
    void shouldLoadCsvDataIntoDatabase() {
        assertTrue(movieRepository.count() > 0);
    }
}