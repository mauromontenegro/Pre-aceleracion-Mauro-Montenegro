package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieDTO {

    private Long id;

    private String image;
    @NotNull
    private String title;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;
    @Size(min = 1, max = 5)
    private Long rating;
    @NotNull
    private Long genreId;

    private List<CharacterDTO> characters;
}
