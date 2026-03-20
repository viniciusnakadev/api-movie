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

        Map<String, Integer> lastWinByProducer = new HashMap<>();

        int minInterval = Integer.MAX_VALUE;
        int maxInterval = Integer.MIN_VALUE;

        List<AwardIntervalItemResponse> minList = new ArrayList<>();
        List<AwardIntervalItemResponse> maxList = new ArrayList<>();

        for (Movie movie : winningMovies) {
            int currentYear = movie.getYear();

            for (String producer : extractProducers(movie.getProducers())) {
                Integer previousYear = lastWinByProducer.put(producer, currentYear);

                if (previousYear == null) {
                    continue;
                }

                int interval = currentYear - previousYear;

                AwardIntervalItemResponse current = new AwardIntervalItemResponse(
                        producer,
                        interval,
                        previousYear,
                        currentYear
                );

                if (interval < minInterval) {
                    minInterval = interval;
                    minList.clear();
                    minList.add(current);
                } else if (interval == minInterval) {
                    minList.add(current);
                }

                if (interval > maxInterval) {
                    maxInterval = interval;
                    maxList.clear();
                    maxList.add(current);
                } else if (interval == maxInterval) {
                    maxList.add(current);
                }
            }
        }

        minList.sort(Comparator.comparing(AwardIntervalItemResponse::producer));
        maxList.sort(Comparator.comparing(AwardIntervalItemResponse::producer));

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
