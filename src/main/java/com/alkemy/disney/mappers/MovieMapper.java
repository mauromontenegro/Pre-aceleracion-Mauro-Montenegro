package com.alkemy.disney.mappers;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.MovieBasicDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    @Autowired
    private CharacterMapper characterMapper;

    /**
     * Mapper for MovieDTO to Entity conversion.
     * @param movieDTO
     * @return
     */
    public Movie movieDTO2Entity(MovieDTO movieDTO) {
        Movie movieEntity = new Movie();
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(movieDTO.getCreationDate());
        movieEntity.setRating(movieDTO.getRating());
        movieEntity.setGenreId(movieDTO.getGenreId());
        // Characters
        Set<Character> characterEntitySet = characterMapper.characterDTOList2EntitySet(movieDTO.getCharacters());
        movieEntity.setCharacters(characterEntitySet);
        return movieEntity;
    }

    /**
     * Mapper for Movie to DTO conversion.
     * @param movieEntity
     * @param loadCharacters
     * @return
     */
    public MovieDTO movieEntity2DTO(Movie movieEntity, boolean loadCharacters) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setCreationDate(movieEntity.getCreationDate());
        movieDTO.setRating(movieEntity.getRating());
        movieDTO.setGenreId(movieEntity.getGenreId());
        if (loadCharacters) {
            List<CharacterDTO> characterDTOS = characterMapper.characterEntitySet2DTOList(movieEntity.getCharacters(), false);
            movieDTO.setCharacters(characterDTOS);
        }
        return movieDTO;
    }

    /**
     * Mapper for Movie Entities List to DTOs List conversion.
     * @param movieEntities
     * @param loadCharacters
     * @return
     */
    public List<MovieDTO> movieEntityList2DTOList(List<Movie> movieEntities, boolean loadCharacters) {
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movieEntities) {
            movieDTOS.add(movieEntity2DTO(movie, loadCharacters));
        }
        return movieDTOS;
    }

    /**
     * Mapper for Movie DTOs List to Entities List conversion.
     * @param movieDTOS
     * @return
     */
    public List<Movie> movieDTOList2EntityList(List<MovieDTO> movieDTOS) {
        List<Movie> movieEntities = new ArrayList<>();
        for (MovieDTO movie : movieDTOS) {
            movieEntities.add(movieDTO2Entity(movie));
        }
        return movieEntities;
    }

    /**
     * Mapper for Movie Entities List to BasicDTO conversion.
     * @param movieEntityList
     * @return
     */
    public List<MovieBasicDTO> movieEntityList2BasicDTOList(List<Movie> movieEntityList) {
        List<MovieBasicDTO> movieBasicDTOList = new ArrayList<>();
        for (Movie movie : movieEntityList) {
            MovieBasicDTO movieBasicDTO = new MovieBasicDTO();
            movieBasicDTO.setImage(movie.getImage());
            movieBasicDTO.setTitle(movie.getTitle());
            movieBasicDTO.setCreationDate(movie.getCreationDate());
            movieBasicDTOList.add(movieBasicDTO);
        }
        return movieBasicDTOList;
    }
}
