package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.MovieBasicDTO;
import com.alkemy.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // CRUD
    /**
     * CREATE a Movie from a MovieDTO and delivering the MovieDTO from the Entity created.
     * @param movieDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<MovieDTO> save(@Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO movie = movieService.save(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    /**
     * UPDATE a movie.
     * @param id
     * @param movieDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO updated = movieService.update(id, movieDTO);
        return ResponseEntity.ok().body(updated);
    }

    /**
     * Soft DELETE a movie.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * CREATE a Movie from a MovieDTO and adding existing characters.
     * @param charactersId
     * @param movieDTO
     * @return
     */
    @PostMapping("/withcharacters")
    public ResponseEntity<MovieDTO> saveWithExistentCharacters(
            @RequestParam(required = false)Set<Long> charactersId,
            @Valid @RequestBody MovieDTO movieDTO
    ) {
        MovieDTO movie = movieService.save(movieDTO);
        movieService.addCharacterList(movie.getId(), charactersId);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    /**
     * Set an existent character to an existent movie.
     * @param idMovie
     * @param idCharacter
     * @return
     */
    @PostMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> setCharacter2Movie(@PathVariable Long idMovie, @PathVariable Long idCharacter) {
        movieService.addCharacter(idMovie, idCharacter);
        return ResponseEntity.ok().body(movieService.getById(idMovie));
    }

    /**
     * Delete a character from an existent movie.
     * @param idMovie
     * @param idCharacter
     * @return
     */
    @DeleteMapping("/movie/{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> deleteCharacterFromMovie(@PathVariable Long idMovie, @PathVariable Long idCharacter) {
        movieService.deleteCharacter(idMovie, idCharacter);
        return ResponseEntity.ok().body(movieService.getById(idMovie));
    }

    /**
     * Get a movie by id.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.getById(id);
        return ResponseEntity.ok().body(movieDTO);
    }

    /**
     * Get Basic Movies (title, image and creation date) by filter.
     * @param name
     * @param genre
     * @param order
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> getMoviesByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) String order
    ) {
        return ResponseEntity.ok().body(movieService.getByFilters(name, genre, order));
    }
}
