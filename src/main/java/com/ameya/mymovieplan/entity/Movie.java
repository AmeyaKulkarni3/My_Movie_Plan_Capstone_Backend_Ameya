package com.ameya.mymovieplan.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate releaseDate;
	
	@Column
	private String directors;
	
	@Column
	private String cast;
	
	@Column
	private String poster;
	
	@Column
	private boolean isActive;

	@ManyToMany(mappedBy = "movies")
	private List<Theater> theaters;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "movies_genres", joinColumns = @JoinColumn(name = "movies_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "genres_id", referencedColumnName = "id"))
	private List<Genre> genres;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "movies_languages", joinColumns = @JoinColumn(name = "movies_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "languages_id", referencedColumnName = "id"))
	private List<Language> languages;

}
