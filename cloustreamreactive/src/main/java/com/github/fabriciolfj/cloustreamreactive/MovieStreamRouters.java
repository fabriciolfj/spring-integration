package com.github.fabriciolfj.cloustreamreactive;

import com.github.fabriciolfj.cloustreamreactive.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MovieStreamRouters {

    @Bean
    public Consumer<Movie> fiction() {
        return movie -> {
            log.info("Science fiction: {}", movie);
        };
    }

    @Bean
    public Consumer<Movie> drama() {
        return movie -> {
            log.info("Drama: {}", movie);
        };
    }
}
