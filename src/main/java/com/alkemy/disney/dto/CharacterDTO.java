package com.alkemy.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
// Useful for hiding null attributes.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {

    private Long id;
    @NotBlank(message = "Character image is required.")
    private String image;
    @NotBlank(message = "Character name is required.")
    private String name;
    @Min(message = "Age must be at least 1.", value = 1)
    private Long age;
    @Min(message = "Weight must be a valid number.", value = 1)
    private Long weight;
    @Size(min=1, max=255, message = "Character story is required (máx 255 characters).")
    private String story;

    private List<MovieDTO> movies;
}
