package com.alkemy.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
// Useful for hiding null attributes.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {

    private Long id;

    private String image;
    @NotNull(message = "Character name is required.")
    private String name;
    @Min(message = "Age must be at least 1.", value = 1)
    private Long age;
    @Min(message = "Weight must be a valid number.", value = 1)
    private Long weight;
    @Max(message = "Story length must be less than 255 characters.", value = 255)
    private String story;

    private List<MovieDTO> movies;
}
