package br.com.vgn.apimovie.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles("test")
class MovieIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    @DisplayName("It should return the min and max structure of the ranges endpoint.")
    void shouldReturnProducerAwardIntervals() {
        restTestClient.get()
                .uri("/movies/awards/intervals")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.min").exists()
                .jsonPath("$.max").exists();
    }

    @Test
    @DisplayName("It must correctly return the producers with the shortest and longest intervals between awards.")
    void shouldReturnAwardIntervalsAccordingToProvidedData() {
        restTestClient.get()
                .uri("/movies/awards/intervals")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.min[0].producer").isEqualTo("Joel Silver")
                .jsonPath("$.min[0].interval").isEqualTo(1)
                .jsonPath("$.min[0].previousWin").isEqualTo(1990)
                .jsonPath("$.min[0].followingWin").isEqualTo(1991)
                .jsonPath("$.max[0].producer").isEqualTo("Matthew Vaughn")
                .jsonPath("$.max[0].interval").isEqualTo(13)
                .jsonPath("$.max[0].previousWin").isEqualTo(2002)
                .jsonPath("$.max[0].followingWin").isEqualTo(2015);
    }
}