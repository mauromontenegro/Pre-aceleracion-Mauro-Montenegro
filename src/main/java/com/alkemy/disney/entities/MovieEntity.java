package com.alkemy.disney.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class MovieEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String image;
	
	private String title;
	
	@Column(name = "creation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate creationDate;
	
	private Long rating;
	
	// Many movies could have the same genre.
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "genre_id", insertable = false, updatable = false)
	private GenreEntity genre;
	
	@Column(name = "genre_id", nullable = false)
	private Long genreId;
	
	@ManyToMany(
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(
			name = "charact_movie",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "charact_id"))
	private Set<CharacterEntity> characters = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MovieEntity other = (MovieEntity) obj;
		return other.id == this.id;
	}
}
