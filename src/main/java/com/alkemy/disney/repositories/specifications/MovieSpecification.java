package com.alkemy.disney.repositories.specifications;

import com.alkemy.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.entities.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieSpecification {

    public Specification<Movie> getByFilters(MovieFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filtersDTO.getTitle().toLowerCase() + "%"
                        )
                );
            }

            if (filtersDTO.getGenreId() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("genreId").as(String.class),
                                "%" + filtersDTO.getGenreId() + "%"
                        )
                );
            }

            // Remove duplicates
            query.distinct(true);

            // Order resolver
            if (filtersDTO.getOrder() != null) {
                String orderByField = "creationDate";
                query.orderBy(
                        filtersDTO.isASC() ?
                                criteriaBuilder.asc(root.get(orderByField)) :
                                criteriaBuilder.desc(root.get(orderByField))
                );
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
