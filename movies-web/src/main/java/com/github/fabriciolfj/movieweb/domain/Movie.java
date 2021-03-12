package com.github.fabriciolfj.movieweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie {

    private String title;
    private String actor;
    private int year;
}
