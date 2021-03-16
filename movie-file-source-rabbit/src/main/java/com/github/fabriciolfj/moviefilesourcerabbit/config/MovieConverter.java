package com.github.fabriciolfj.moviefilesourcerabbit.config;

import com.github.fabriciolfj.moviefilesourcerabbit.domain.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieConverter implements Converter<String, Movie> {

    @Override
    public Movie convert(final String s) {
        final var fields = Stream.of(s.split(",")).map(String::trim).collect(Collectors.toList());
        return new Movie(fields.get(0), Integer.valueOf(fields.get(1)));
    }
}
