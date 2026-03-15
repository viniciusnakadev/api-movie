package br.com.vgn.apimovie.domain.service;

import br.com.vgn.apimovie.api.dto.AwardIntervalItemResponse;
import br.com.vgn.apimovie.api.dto.AwardIntervalResponse;
import br.com.vgn.apimovie.domain.entity.Movie;
import br.com.vgn.apimovie.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * method created for calculated the winners producers
     * @return AwardIntervalResponse
     */
    public AwardIntervalResponse getProducerAwardIntervals() {
        List<Movie> winningMovies = movieRepository.findByWinnerGoldenRaspberryAwardsTrueOrderByYearAsc();

        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : winningMovies) {
            List<String> producers = extractProducers(movie.getProducers());

            for (String producer : producers) {
                producerWins.computeIfAbsent(producer, key -> new ArrayList<>())
                        .add(movie.getYear());
            }
        }

        List<AwardIntervalItemResponse> intervals = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();

            // winner < 2 continue
            if (years.size() < 2) {
                continue;
            }

            years.sort(Integer::compareTo);

            for (int i = 1; i < years.size(); i++) {
                int previous = years.get(i - 1);
                int following = years.get(i);
                int interval = following - previous;

                intervals.add(new AwardIntervalItemResponse(
                        producer,
                        interval,
                        previous,
                        following
                ));
            }
        }

        if (intervals.isEmpty()) {
            return new AwardIntervalResponse(List.of(), List.of());
        }

        int minInterval = intervals.stream()
                .map(AwardIntervalItemResponse::interval)
                .min(Integer::compareTo)
                .orElse(0);

        int maxInterval = intervals.stream()
                .map(AwardIntervalItemResponse::interval)
                .max(Integer::compareTo)
                .orElse(0);

        List<AwardIntervalItemResponse> minList = intervals.stream()
                .filter(item -> item.interval() == minInterval)
                .sorted(Comparator.comparing(AwardIntervalItemResponse::producer))
                .toList();

        List<AwardIntervalItemResponse> maxList = intervals.stream()
                .filter(item -> item.interval() == maxInterval)
                .sorted(Comparator.comparing(AwardIntervalItemResponse::producer))
                .toList();

        return new AwardIntervalResponse(minList, maxList);
    }

    /**
     * method created for replace 'and' for ','  and split for ',' and retorne a List with all producers
     * @param producers
     * @return
     */
    private List<String> extractProducers(String producers) {
        return Arrays.stream(producers.replace(" and ", ",").split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
