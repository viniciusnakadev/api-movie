package br.com.vgn.apimovie.domain.loader;

import br.com.vgn.apimovie.domain.entity.Movie;
import br.com.vgn.apimovie.domain.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieCsvLoader {

    private final MovieRepository movieRepository;

    @Value("${app.csv.file-path:files/Movielist.csv}")
    private String csvFilePath;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void loadCsv() throws IOException {

        ClassPathResource resource = new ClassPathResource(csvFilePath);
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(";", -1);

                movies.add(Movie.builder()
                        .year(parseYear(columns))
                        .title(parseTitle(columns))
                        .studios(parseStudios(columns))
                        .producers(parseProducers(columns))
                        .winnerGoldenRaspberryAwards(parseWinner(columns))
                        .build());
            }
        }
        movieRepository.saveAll(movies);
    }

    private Integer parseYear(String[] columns) {
        return Integer.parseInt(columns[0].trim());
    }

    private String parseTitle(String[] columns) {
        return columns[1].trim();
    }

    private String parseStudios(String[] columns) {
        return columns[2].trim();
    }

    private String parseProducers(String[] columns) {
        return columns[3].trim();
    }

    private boolean parseWinner(String[] columns) {
        return columns.length > 4 && "yes".equalsIgnoreCase(columns[4].trim());
    }
}