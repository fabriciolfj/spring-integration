package com.github.fabriciolfj.movie.infra.util;

import com.github.fabriciolfj.movie.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class MovieConverter implements Converter<String, Movie> {

    @Override
    public Movie convert(final String source) {
        log.debug(source);
        var fields = Stream.of(source.split((",")))
                .map(String::trim)
                .collect(Collectors.toList());

        return new Movie(fields.get(0), fields.get(1), Integer.valueOf(fields.get(2)));
    }
}
