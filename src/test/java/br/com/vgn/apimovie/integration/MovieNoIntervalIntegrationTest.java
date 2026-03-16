package br.com.vgn.apimovie.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles("test")
@TestPropertySource(properties = "app.csv.file-path=files/Movielist-no-interval.csv")
class MovieNoIntervalIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    @DisplayName("It should return empty lists when no producer has two wins")
    void shouldReturnEmptyMinAndMaxWhenNoProducerHasTwoWins() {
        restTestClient.get()
                .uri("/movies/awards/intervals")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.min.length()").isEqualTo(0)
                .jsonPath("$.max.length()").isEqualTo(0);
    }
}
