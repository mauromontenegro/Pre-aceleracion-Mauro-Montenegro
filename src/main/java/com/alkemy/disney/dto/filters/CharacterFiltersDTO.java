package com.alkemy.disney.dto.filters;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CharacterFiltersDTO {
    private String name;
    private Long age;
    private Long weight;
    private Set<Long> movies;

    // Constructor
    public CharacterFiltersDTO(String name, Long age, Long weight, Set<Long> movies) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.movies = movies;
    }
}
