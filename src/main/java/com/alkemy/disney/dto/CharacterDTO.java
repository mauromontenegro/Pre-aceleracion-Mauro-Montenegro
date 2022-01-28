package com.alkemy.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
// Useful for hiding null attributes.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {

    private Long id;

    private String image;
    @NotNull
    private String name;
    @Size(min = 1)
    private Long age;
    @Size(min = 1)
    private Long weight;
    @Size(max = 255)
    private String story;

    private List<MovieDTO> movies;
}
