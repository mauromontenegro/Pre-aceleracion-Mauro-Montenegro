package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    // CRUD
    /**
     * CREATE a genre from a GenreDTO and delivering the GenreDTO from the Entity created.
     * @param genreDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GenreDTO> save(@Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO genre = genreService.save(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    /**
     * Soft DELETE a genre.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Get all genres from the database.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok().body(genres);
    }
}
