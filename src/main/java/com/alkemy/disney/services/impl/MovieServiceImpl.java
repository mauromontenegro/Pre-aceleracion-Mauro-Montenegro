package com.alkemy.disney.services.impl;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.MovieBasicDTO;
import com.alkemy.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.mappers.CharacterMapper;
import com.alkemy.disney.mappers.MovieMapper;
import com.alkemy.disney.repositories.MovieRepository;
import com.alkemy.disney.repositories.specifications.MovieSpecification;
import com.alkemy.disney.services.CharacterService;
import com.alkemy.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class MovieServiceImpl implements MovieService {

    // Mapper
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private CharacterMapper characterMapper;

    // Repository
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieSpecification movieSpecification;

    // Service
    @Autowired
    private CharacterService characterService;

    // CRUD

    /**
     * CREATE a Movie
     * @param movieDTO
     * @return
     */
    public MovieDTO save(MovieDTO movieDTO) {
        Movie movieEntity = movieMapper.movieDTO2Entity(movieDTO);
        Movie movieEntitySaved = movieRepository.save(movieEntity);
        return movieMapper.movieEntity2DTO(movieEntitySaved, true);
    }

    /**
     * UPDATE a Movie
     * @param id
     * @param movieDTO
     * @return
     */
    public MovieDTO update(Long id, MovieDTO movieDTO) {
        Movie movieEntity = movieRepository.getById(id);
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(movieDTO.getCreationDate());
        movieEntity.setRating(movieDTO.getRating());
        movieEntity.setGenreId(movieDTO.getGenreId());
        Movie movieEntitySaved = movieRepository.save(movieEntity);
        return movieMapper.movieEntity2DTO(movieEntitySaved, true);
    }

    /**
     * Soft DELETE a movie.
     * @param id
     */
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    /**
     * Add a new character to a movie.
     * @param idMovie
     * @param idCharacter
     */
    public void addCharacter(Long idMovie, Long idCharacter) {
        Movie movie = getEntityById(idMovie);
        Set<Character> characters = movie.getCharacters();
        characters.add(characterService.getEntityById(idCharacter));
        movie.setCharacters(characters);
        movieRepository.save(movie);
    }

    /**
     * Delete a character from a movie.
     * @param idMovie
     * @param idCharacter
     */
    public void deleteCharacter(Long idMovie, Long idCharacter) {
        Movie movie = getEntityById(idMovie);
        Set<Character> characters = movie.getCharacters();
        characters.remove(characterService.getEntityById(idCharacter));
        movie.setCharacters(characters);
        movieRepository.save(movie);
    }

    /**
     * Add a new character to a movie.
     * @param idMovie
     * @param charactersId
     */
    public void addCharacterList(Long idMovie, Set<Long> charactersId) {
        Movie movie = getEntityById(idMovie);
        Set<Character> movieCharacters = movie.getCharacters();
        for (Long id : charactersId) {
            movieCharacters.add(characterService.getEntityById(id));
        }
        movie.setCharacters(movieCharacters);
        movieRepository.save(movie);
    }

    /**
     * Get all basic Movies (image, title and creation date) as a list.
     * @return
     */
    public List<MovieBasicDTO> getAllBasicMovies() {
        List<Movie> movieEntityList = movieRepository.findAll();
        return movieMapper.movieEntityList2BasicDTOList(movieEntityList);
    }

    /**
     * Get all Movies from the database.
     * @return
     */
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movieMapper.movieEntityList2DTOList(movies, true);
    }

    /**
     * Get one movie by id.
     * @param id
     * @return
     */
    public MovieDTO getById(Long id) {
        Movie movieEntity = movieRepository.getById(id);
        MovieDTO movieDTO = movieMapper.movieEntity2DTO(movieEntity, true);
        return movieDTO;
    }

    /**
     * Get movies by filter.
     * @param title
     * @param genreId
     * @param order
     * @return
     */
    public List<MovieBasicDTO> getByFilters(String title, Long genreId, String order) {
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, genreId, order);
        List<Movie> entities = movieRepository.findAll(movieSpecification.getByFilters(filtersDTO));
        return movieMapper.movieEntityList2BasicDTOList(entities);
    }

    /**
     * Get a movieEntity by id.
     * @param id
     * @return
     */
    public Movie getEntityById(Long id) {
        return movieRepository.getById(id);
    }
}
