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
@TestPropertySource(properties = "app.csv.file-path=files/Movielist-minimum-draw.csv")
public class MovieMinDrawIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    @DisplayName("It should return a tie in the shorter interval and a single producer in the longer interval")
    void shouldReturnTieForMinInterval() {
        restTestClient.get()
                .uri("/movies/awards/intervals")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.min.length()").isEqualTo(2)
                .jsonPath("$.min[0].producer").isEqualTo("Producer Test 1")
                .jsonPath("$.min[0].interval").isEqualTo(1)
                .jsonPath("$.min[0].previousWin").isEqualTo(2000)
                .jsonPath("$.min[0].followingWin").isEqualTo(2001)
                .jsonPath("$.min[1].producer").isEqualTo("Producer Test 2")
                .jsonPath("$.min[1].interval").isEqualTo(1)
                .jsonPath("$.min[1].previousWin").isEqualTo(2012)
                .jsonPath("$.min[1].followingWin").isEqualTo(2013)
                .jsonPath("$.max.length()").isEqualTo(1)
                .jsonPath("$.max[0].producer").isEqualTo("Producer Test 3")
                .jsonPath("$.max[0].interval").isEqualTo(18)
                .jsonPath("$.max[0].previousWin").isEqualTo(2002)
                .jsonPath("$.max[0].followingWin").isEqualTo(2020);
    }

}
