package br.com.vgn.apimovie.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "year_movie", nullable = false)
    private Integer year;

    @Column(nullable = false, length = 100)
    private String studios;

    @Column(nullable = false)
    private String producers;

    @Column(name = "winner", nullable = false)
    private boolean winnerGoldenRaspberryAwards;

}
