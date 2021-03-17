package com.github.fabriciolfj.moviesinkkafka;

import com.github.fabriciolfj.moviesinkkafka.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MovieStream {

    @Bean
    public Consumer<Movie> log() {
        return movie -> {
            log.info("Movie processed: {}", movie);
        };
    }
}
