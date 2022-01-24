package com.alkemy.disney.repositories.specifications;

import com.alkemy.disney.dto.filters.CharacterFiltersDTO;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

@Component
public class CharacterSpecification {

    public Specification<Character> getByFilters(CharacterFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            if (filtersDTO.getAge() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("age").as(String.class),
                                "%" + String.valueOf(filtersDTO.getAge()) + "%"
                        )
                );
            }

            if (filtersDTO.getWeight() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("weight").as(String.class),
                                "%" + filtersDTO.getWeight() + "%"
                        )
                );
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getMovies())) {
                Join<Movie, Character> join = root.join("movies", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filtersDTO.getMovies()));
            }

            // Remove duplicates
            query.distinct(true);

            // Order resolver
            String orderByField = "name";
            query.orderBy(criteriaBuilder.asc(root.get(orderByField))); // Ordering just ASC

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
