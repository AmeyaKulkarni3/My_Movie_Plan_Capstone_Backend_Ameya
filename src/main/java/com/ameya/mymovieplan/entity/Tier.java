package com.ameya.mymovieplan.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tiers")
@Data
public class Tier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int seats;
	
	@Column(nullable = false)
	private double price;
	
	@ManyToMany(mappedBy = "tiers")
	private List<Theater> theaters;
	
	@Column(nullable = false)
	private int priority;

}
