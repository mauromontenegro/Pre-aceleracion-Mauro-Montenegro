package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieDTO {

    private Long id;

    private String image;

    private String title;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    private Long rating;

    private Long genreId;

    private List<CharacterDTO> characters;
}
