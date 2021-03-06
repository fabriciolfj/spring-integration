package com.github.fabriciolfj.movieprocessorkafka.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    private String title;
    private String actor;
    private int year;
    private String genre;
}
