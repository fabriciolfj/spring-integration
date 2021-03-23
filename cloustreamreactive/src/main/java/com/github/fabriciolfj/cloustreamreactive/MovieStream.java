package com.github.fabriciolfj.cloustreamreactive;

import com.github.fabriciolfj.cloustreamreactive.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Function;

@Slf4j
@Configuration
public class MovieStream {

    String GENRE = "teste";

    @Bean
    public Function<Tuple2<Flux<Movie>, Flux<Movie>>, Flux<Movie>> onlyGenreTest() {
        return flux ->
            flux.getT1()
                    .filter(movie -> {
                log.info("Valor tipo 2: {}", flux.getT2().blockFirst().toString());
                log.info("Filter: {}", movie);
                return movie.getGenre().equalsIgnoreCase(GENRE);
            });
    }

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> titleUpperCase() {
        return movieFlux -> movieFlux.map(movie -> {
            log.info("Uppercase: {}", movie);
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        });
    }
}
