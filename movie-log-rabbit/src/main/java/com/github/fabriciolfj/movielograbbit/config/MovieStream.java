package com.github.fabriciolfj.movielograbbit.config;

import com.github.fabriciolfj.movielograbbit.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class MovieStream {

    @StreamListener(Sink.INPUT)
    public void process(final Movie movie) {
        log.info("Movie processed: {}", movie);
    }
}
