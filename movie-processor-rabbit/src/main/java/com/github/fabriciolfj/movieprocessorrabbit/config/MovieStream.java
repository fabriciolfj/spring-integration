package com.github.fabriciolfj.movieprocessorrabbit.config;

import com.github.fabriciolfj.movieprocessorrabbit.domain.Movie;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
public class MovieStream {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Movie process(Movie movie) {
        movie.setTitle(movie.getTitle().toUpperCase());
        return movie;
    }
}
