package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Character;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findAll(Specification<Character> spec);
}
