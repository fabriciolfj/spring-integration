package com.github.fabriciolfj.movieintegration.adapters;

import com.github.fabriciolfj.movieintegration.domain.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Log4j2
@Configuration
public class MovieStream {

    @Bean
    public Consumer<Movie> log() {
        return movie -> {
            log.info("Movie received: {}", movie);
        };
    }
}
