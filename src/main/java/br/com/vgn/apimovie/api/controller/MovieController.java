package br.com.vgn.apimovie.api.controller;

import br.com.vgn.apimovie.api.dto.AwardIntervalResponse;
import br.com.vgn.apimovie.domain.service.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl movieService;

    @GetMapping("/awards/intervals")
    public AwardIntervalResponse getAwardIntervals() {
        return movieService.getProducerAwardIntervals();
    }

}
