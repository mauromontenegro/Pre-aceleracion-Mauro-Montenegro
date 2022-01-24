package com.alkemy.disney.mappers;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.basics.CharacterBasicDTO;
import com.alkemy.disney.entities.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
     * Mapper for CharacterDTOList to CharacterEntitySet conversion.
     * @param characterDTOList
     * @return
     */
    public Set<Character> characterDTOList2EntitySet(List<CharacterDTO> characterDTOList) {
        Set<Character> characterEntitySet = new HashSet<>();
        for (CharacterDTO character : characterDTOList) {
            characterEntitySet.add(characterDTO2Entity(character));
        }
        return characterEntitySet;
    }

    /**
     * Mapper for CharacterEntitySet to CharacterDTOList conversion.
     * @param characterEntitySet
     * @param loadMovies
     * @return
     */
    public List<CharacterDTO> characterEntitySet2DTOList(Collection<Character> characterEntitySet, boolean loadMovies) {
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        for (Character character : characterEntitySet) {
            characterDTOList.add(characterEntity2DTO(character, loadMovies));
        }
        return characterDTOList;
    }

    /**
     * Mapper for character Entity List to Basic DTO List conversion.
     * @param characterEntityList
     * @return
     */
    public List<CharacterBasicDTO> characterEntityList2BasicDTOList(List<Character> characterEntityList) {
        List<CharacterBasicDTO> characterBasicDTOList = new ArrayList<>();
        for (Character character : characterEntityList) {
            CharacterBasicDTO characterBasicDTO = new CharacterBasicDTO();
            characterBasicDTO.setName(character.getName());
            characterBasicDTO.setImage(character.getImage());
            characterBasicDTOList.add(characterBasicDTO);
        }
        return characterBasicDTOList;
    }
}
