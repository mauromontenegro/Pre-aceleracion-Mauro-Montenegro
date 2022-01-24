package com.alkemy.disney.services;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.basics.CharacterBasicDTO;
import com.alkemy.disney.entities.Character;

import java.util.List;
import java.util.Set;

public interface CharacterService {

    CharacterDTO save(CharacterDTO characterDTO, Long idMovie);

    CharacterDTO update(Long id, CharacterDTO characterDTO);

    void delete(Long id);

    List<CharacterBasicDTO> getAllBasicCharacters();

    CharacterDTO getById(Long id);

    List<CharacterBasicDTO> getByFilters(String name, Long age, Long weight, Set<Long> movies);

    Character getEntityById(Long id);

    List<CharacterDTO> getAllCharacters();
}
