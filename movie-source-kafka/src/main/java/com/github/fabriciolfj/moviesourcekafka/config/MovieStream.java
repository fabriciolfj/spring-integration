package com.github.fabriciolfj.moviesourcekafka.config;

import com.github.fabriciolfj.moviesourcekafka.domain.Movie;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Configuration
public class MovieStream {

    @PollableBean
    public Supplier<Flux<Movie>> movie() {
        return () -> Flux.just(new Movie("Matrix", "Keanu", 1999, "science"), new Movie("it", "bill", 2017, "horror"));
    }
}
