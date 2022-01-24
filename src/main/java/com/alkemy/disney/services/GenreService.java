package com.alkemy.disney.services;

import com.alkemy.disney.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO save(GenreDTO genreDTO);

    List<GenreDTO> getAllGenres();

    void delete(Long id);
}
