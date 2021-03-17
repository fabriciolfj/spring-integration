package com.github.fabriciolfj.movieprocessorkafka;

import com.github.fabriciolfj.movieprocessorkafka.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class MovieStream {

    @Bean
    public Function<Movie, Movie> uppercase() {
        return movie -> {
            log.info("Processing: {}", movie);
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        };
    }
}
