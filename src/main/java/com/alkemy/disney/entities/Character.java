package com.alkemy.disney.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "charact")
@Getter
@Setter
@SQLDelete(sql = "UPDATE charact SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String image;
	
	private String name;
	
	private Long age;
	
	private Long weight;
	
	private String story;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "characters", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Movie> movies = new ArrayList<>();

	private boolean deleted = Boolean.FALSE;

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Character other = (Character) obj;
		return other.id == this.id;
	}
}
