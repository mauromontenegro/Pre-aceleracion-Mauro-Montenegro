package com.alkemy.disney.services;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.MovieBasicDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Movie;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO movieDTO);

    MovieDTO update(Long id, MovieDTO movieDTO);

    void delete(Long id);

    void addCharacter(Long idMovie, Long idCharacter);

    void deleteCharacter(Long idMovie, Long idCharacter);

    void addCharacterList(Long idMovie, Set<Long> charactersId);

    List<MovieBasicDTO> getAllBasicMovies();

    MovieDTO getById(Long id);

    List<MovieBasicDTO> getByFilters(String title, Long genreId, String order);

    Movie getEntityById(Long id);

    List<MovieDTO> getAllMovies();
}
