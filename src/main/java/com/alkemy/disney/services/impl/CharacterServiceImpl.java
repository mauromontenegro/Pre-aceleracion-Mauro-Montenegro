package com.alkemy.disney.services.impl;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.basics.CharacterBasicDTO;
import com.alkemy.disney.dto.filters.CharacterFiltersDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.exceptions.ParamNotFound;
import com.alkemy.disney.mappers.CharacterMapper;
import com.alkemy.disney.repositories.CharacterRepository;
import com.alkemy.disney.repositories.specifications.CharacterSpecification;
import com.alkemy.disney.services.CharacterService;
import com.alkemy.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterServiceImpl implements CharacterService {

    // Mapper
    @Autowired
    private CharacterMapper characterMapper;

    // Repository
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterSpecification characterSpecification;

    // Service
    @Autowired
    private MovieService movieService;

    // CRUD
    /**
     * CREATE a Character
     * @param characterDTO
     * @param idMovie
     * @return
     */
    public CharacterDTO save(CharacterDTO characterDTO, Long idMovie) {
        Character characterEntity = characterMapper.characterDTO2Entity(characterDTO);
        // Saving the entity.
        Character characterEntitySaved = characterRepository.save(characterEntity);
        // Setting the character to the movie.
        movieService.addCharacter(idMovie, characterEntitySaved.getId());
        return characterMapper.characterEntity2DTO(characterEntitySaved, true);
    }

    /**
     * UPDATE a Character
     * @param id
     * @param characterDTO
     * @return
     */
    public CharacterDTO update(Long id, CharacterDTO characterDTO) {
        Optional<Character> characterEntity = characterRepository.findById(id);
        if (!characterEntity.isPresent()) {
            throw new ParamNotFound("Character Id not valid.");
        }
        characterEntity.get().setImage(characterDTO.getImage());
        characterEntity.get().setName(characterDTO.getName());
        characterEntity.get().setAge(characterDTO.getAge());
        characterEntity.get().setWeight(characterDTO.getWeight());
        characterEntity.get().setStory(characterDTO.getStory());
        characterRepository.save(characterEntity.get());
        return characterMapper.characterEntity2DTO(characterEntity.get(), false);
    }

    /**
     * Soft DELETE a character.
     * @param id
     */
    public void delete(Long id) {characterRepository.deleteById(id);}

    /**
     * Get all basic Characters (image and name) as a list.
     * @return
     */
    public List<CharacterBasicDTO> getAllBasicCharacters() {
        List<Character> characterEntityList = characterRepository.findAll();
        return characterMapper.characterEntityList2BasicDTOList(characterEntityList);
    }

    /**
     * Get all Characters from the database.
     * @return
     */
    public List<CharacterDTO> getAllCharacters() {
        List<Character> characterEntityList = characterRepository.findAll();
        Set<Character> characterEntities = new HashSet<>();
        for (Character character : characterEntityList) {
            characterEntities.add(character);
        }
        return characterMapper.characterEntitySet2DTOList(characterEntities, true);
    }

    /**
     * Get one character by id.
     * @param id
     * @return
     */
    public CharacterDTO getById(Long id) {
        Optional<Character> characterEntity = characterRepository.findById(id);
        if (!characterEntity.isPresent()) {
            throw new ParamNotFound("Character Id not valid.");
        }
        CharacterDTO characterDTO = characterMapper.characterEntity2DTO(characterEntity.get(), true);
        return characterDTO;
    }

    /**
     * Get characters by filter.
     * @param name
     * @param age
     * @param weight
     * @param movies
     * @return
     */
    public List<CharacterBasicDTO> getByFilters(String name, Long age, Long weight, Set<Long> movies) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);
        List<Character> entities = characterRepository.findAll(characterSpecification.getByFilters(filtersDTO));
        if (entities.isEmpty()) {
            throw new ParamNotFound("No characters found with the indicated parameters.");
        }
        return characterMapper.characterEntityList2BasicDTOList(entities);
    }

    /**
     * Get a characterEntity by id.
     * @param id
     * @return
     */
    public Character getEntityById(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if (!character.isPresent()) {
            throw new ParamNotFound("Character Id not valid.");
        }
        return character.get();
    }
}
