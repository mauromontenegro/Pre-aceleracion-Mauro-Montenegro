package com.alkemy.disney.services.impl;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.MovieBasicDTO;
import com.alkemy.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.exceptions.ParamNotFound;
import com.alkemy.disney.mappers.CharacterMapper;
import com.alkemy.disney.mappers.MovieMapper;
import com.alkemy.disney.repositories.MovieRepository;
import com.alkemy.disney.repositories.specifications.MovieSpecification;
import com.alkemy.disney.services.CharacterService;
import com.alkemy.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<Movie> movieEntity = movieRepository.findById(id);
        if (!movieEntity.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        movieEntity.get().setImage(movieDTO.getImage());
        movieEntity.get().setTitle(movieDTO.getTitle());
        movieEntity.get().setCreationDate(movieDTO.getCreationDate());
        movieEntity.get().setRating(movieDTO.getRating());
        movieEntity.get().setGenreId(movieDTO.getGenreId());
        Movie movieEntitySaved = movieRepository.save(movieEntity.get());
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
        Optional<Movie> movie = getEntityById(idMovie);
        if (!movie.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        Set<Character> characters = movie.get().getCharacters();
        characters.add(characterService.getEntityById(idCharacter));
        movie.get().setCharacters(characters);
        movieRepository.save(movie.get());
    }

    /**
     * Delete a character from a movie.
     * @param idMovie
     * @param idCharacter
     */
    public void deleteCharacter(Long idMovie, Long idCharacter) {
        Optional<Movie> movie = getEntityById(idMovie);
        if (!movie.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        Set<Character> characters = movie.get().getCharacters();
        characters.remove(characterService.getEntityById(idCharacter));
        movie.get().setCharacters(characters);
        movieRepository.save(movie.get());
    }

    /**
     * Add a new character to a movie.
     * @param idMovie
     * @param charactersId
     */
    public void addCharacterList(Long idMovie, Set<Long> charactersId) {
        Optional <Movie> movie = getEntityById(idMovie);
        if (!movie.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        Set<Character> movieCharacters = movie.get().getCharacters();
        for (Long id : charactersId) {
            movieCharacters.add(characterService.getEntityById(id));
        }
        movie.get().setCharacters(movieCharacters);
        movieRepository.save(movie.get());
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
        Optional<Movie> movieEntity = movieRepository.findById(id);
        if (!movieEntity.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        MovieDTO movieDTO = movieMapper.movieEntity2DTO(movieEntity.get(), true);
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
        if (entities.isEmpty()) {
            throw new ParamNotFound("No movies found with the indicated parameters.");
        }
        return movieMapper.movieEntityList2BasicDTOList(entities);
    }

    /**
     * Get a movieEntity by id.
     * @param id
     * @return
     */
    public Optional<Movie> getEntityById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (!movie.isPresent()) {
            throw new ParamNotFound("Movie Id not valid.");
        }
        return movie;
    }
}
