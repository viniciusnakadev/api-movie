package br.com.vgn.apimovie.domain.repository;

import br.com.vgn.apimovie.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("movieRepository")
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    List<Movie> findByWinnerGoldenRaspberryAwardsTrueOrderByYearAsc();
}
