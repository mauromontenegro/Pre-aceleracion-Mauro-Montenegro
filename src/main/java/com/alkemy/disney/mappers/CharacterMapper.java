package com.alkemy.disney.mappers;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.CharacterBasicDTO;
import com.alkemy.disney.entities.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    /**
     * Mapper for CharacterDTO to Entity conversion.
     * @param characterDTO
     * @return
     */
    public Character characterDTO2Entity(CharacterDTO characterDTO) {
        Character characterEntity = new Character();
        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setStory(characterDTO.getStory());
        return characterEntity;
    }

    /**
     * Mapper for Character to DTO conversion.
     * @param characterEntity
     * @param loadMovies
     * @return
     */
    public CharacterDTO characterEntity2DTO(Character characterEntity, boolean loadMovies) {
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(characterEntity.getId());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setStory(characterEntity.getStory());
        if (loadMovies) {
            List<MovieDTO> movieDTOS = movieMapper.movieEntityList2DTOList(characterEntity.getMovies(), false);
            characterDTO.setMovies(movieDTOS);
        }
        return characterDTO;
    }

    /**
     * Mapper for Character to BasicDTO conversion.
     * @param characterEntity
     * @return
     */
    public CharacterBasicDTO characterEntity2BasicDTO(Character characterEntity) {
        CharacterBasicDTO characterBasicDTO = new CharacterBasicDTO();
        characterBasicDTO.setName(characterEntity.getName());
        characterBasicDTO.setImage(characterEntity.getImage());
        return characterBasicDTO;
    }

    /**
     * Mapper for CharacterDTOList to CharacterEntitySet conversion.
     * @param characterDTOList
     * @return
     */
    public Set<Character> characterDTOList2EntitySet(List<CharacterDTO> characterDTOList) {
        return characterDTOList.stream().map(characterDTO -> characterDTO2Entity(characterDTO)).collect(Collectors.toSet());
    }

    /**
     * Mapper for CharacterEntitySet to CharacterDTOList conversion.
     * @param characterEntitySet
     * @param loadMovies
     * @return
     */
    public List<CharacterDTO> characterEntitySet2DTOList(Collection<Character> characterEntitySet, boolean loadMovies) {
        return characterEntitySet.stream().map(character -> characterEntity2DTO(character, loadMovies)).collect(Collectors.toList());
    }

    /**
     * Mapper for character Entity List to Basic DTO List conversion.
     * @param characterEntityList
     * @return
     */
    public List<CharacterBasicDTO> characterEntityList2BasicDTOList(List<Character> characterEntityList) {
        return characterEntityList.stream().map(character -> characterEntity2BasicDTO(character)).collect(Collectors.toList());
    }
}
