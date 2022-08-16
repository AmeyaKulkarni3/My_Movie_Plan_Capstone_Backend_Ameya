package com.ameya.mymovieplan.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="schedules")
@Data
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Movie movie;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Theater theater;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Showtime showtime;
	
	@Column
	private LocalDate date;
	
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
	private List<Seat> seats;

}
