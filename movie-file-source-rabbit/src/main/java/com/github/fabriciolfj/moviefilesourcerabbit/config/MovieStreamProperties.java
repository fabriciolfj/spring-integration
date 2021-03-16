package com.github.fabriciolfj.moviefilesourcerabbit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie.stream")
public class MovieStreamProperties {

    private String directory;
    private String namePattern;
}
