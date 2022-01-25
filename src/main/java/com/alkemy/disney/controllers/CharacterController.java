package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.basics.CharacterBasicDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieController movieController;

    // CRUD
    /**
     * CREATE a character and set it to an existent movie.
     * @param idMovie
     * @param characterDTO
     */
    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestParam Long idMovie, @RequestBody CharacterDTO characterDTO) {
        return ResponseEntity.ok().body(characterService.save(characterDTO, idMovie));
    }

    /**
     * UPDATE a character.
     * @param id
     * @param characterDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO characterDTO) {
        CharacterDTO updated = characterService.update(id, characterDTO);
        return ResponseEntity.ok().body(updated);
    }

    /**
     * Soft DELETE a character.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Get a character by id.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getById(@PathVariable Long id) {
        CharacterDTO characterDTO = characterService.getById(id);
        return ResponseEntity.ok().body(characterDTO);
    }

    /**
     * Get all Basic Characters (image and name), or selected by filter.
     * @param name
     * @param age
     * @param weight
     * @param movies
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getCharactersByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long age,
            @RequestParam(required = false) Long weight,
            @RequestParam(required = false) Set<Long> movies
    ) {
        return ResponseEntity.ok().body(characterService.getByFilters(name, age, weight, movies));
    }
}
