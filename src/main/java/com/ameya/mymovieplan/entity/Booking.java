package com.ameya.mymovieplan.entity;

import java.util.List;

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
@Table(name="bookings")
@Data
public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Schedule schedule;
	
	@OneToMany(mappedBy = "booking")
	private List<Seat> seats;
	
	@ManyToOne
	private UserEntity user;
	
	@Column
	private double totalPrice;

}
