package com.alkemy.disney.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "charact")
@Getter
@Setter
public class CharacterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String image;
	
	private String name;
	
	private Long age;
	
	private Long weight;
	
	private String story;
	
	@ManyToMany(mappedBy = "characters", cascade = CascadeType.ALL)
	private List<MovieEntity> movies = new ArrayList<>();
	
	// Add and remove movies
	
	public void addMovie(MovieEntity movie) {this.movies.add(movie);}
	
	public void removeMovie(MovieEntity movie) {this.movies.remove(movie);}
}
