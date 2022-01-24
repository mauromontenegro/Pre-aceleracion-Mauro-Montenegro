package com.alkemy.disney.mappers;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entities.Genre;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapper {

    /**
     * Mapper for GenreDTO to Entity conversion.
     * @param genreDTO
     * @return
     */
    public Genre genreDTO2Entity(GenreDTO genreDTO) {
        Genre genreEntity = new Genre();
        genreEntity.setName(genreDTO.getName());
        genreEntity.setImage(genreDTO.getImage());
        return genreEntity;
    }

    /**
     * Mapper for Genre to DTO conversion.
     * @param genreEntity
     * @return
     */
    public GenreDTO genreEntity2DTO(Genre genreEntity) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genreEntity.getId());
        genreDTO.setName(genreEntity.getName());
        genreDTO.setImage(genreEntity.getImage());
        return genreDTO;
    }

    /**
     * Mapper for Genre Entities list to DTOs List conversion.
     * @param genreEntities
     * @return
     */
    public List<GenreDTO> genreEntityList2DTOList(List<Genre> genreEntities) {
        List<GenreDTO> genreDTOS = new ArrayList<>();
        for (Genre genre : genreEntities) {
            genreDTOS.add(genreEntity2DTO(genre));
        }
        return genreDTOS;
    }
}
