package com.alkemy.disney.services.impl;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entities.Genre;
import com.alkemy.disney.mappers.GenreMapper;
import com.alkemy.disney.repositories.GenreRepository;
import com.alkemy.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreRepository genreRepository;

    /**
     * CREATE a Genre.
     * @param genreDTO
     * @return
     */
    public GenreDTO save(GenreDTO genreDTO) {
        Genre genreEntity = genreMapper.genreDTO2Entity(genreDTO);
        genreRepository.save(genreEntity);
        return genreMapper.genreEntity2DTO(genreEntity);
    }

    /**
     * Soft DELETE a genre.
     * @param id
     */
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    /**
     * Get all Genres from the database.
     * @return
     */
    public List<GenreDTO> getAllGenres() {
        List<Genre> genreEntities = genreRepository.findAll();
        return genreMapper.genreEntityList2DTOList(genreEntities);
    }
}
