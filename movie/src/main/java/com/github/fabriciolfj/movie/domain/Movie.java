package com.github.fabriciolfj.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Movie {
    private String title;
    private String actor;
    private int year;
}
