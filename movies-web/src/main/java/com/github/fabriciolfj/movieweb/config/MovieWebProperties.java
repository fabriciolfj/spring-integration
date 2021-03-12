package com.github.fabriciolfj.movieweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie-web")
public class MovieWebProperties {

    private String path;
}
