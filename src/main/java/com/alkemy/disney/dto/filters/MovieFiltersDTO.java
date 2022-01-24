package com.alkemy.disney.dto.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFiltersDTO {
    private String title;
    private Long genreId;
    private String order;

    // Constructor
    public MovieFiltersDTO(String title, Long genreId, String order) {
        this.title = title;
        this.genreId = genreId;
        this.order = order;
    }
    // Order
    public boolean isASC() {return order.compareToIgnoreCase("ASC") == 0;}
}
