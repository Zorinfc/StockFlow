package com.tobeto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
public class Shelf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int no;
	private int item_quantity;
	private int capacity;
	@OneToOne
	private Item item;
}
