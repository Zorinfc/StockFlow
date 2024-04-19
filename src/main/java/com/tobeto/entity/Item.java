package com.tobeto.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = { "shelf", "reports" })
@EqualsAndHashCode(exclude = { "shelf", "reports" })
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String name;
	private int min_quantity;

	@JsonIgnore
	@OneToMany(mappedBy = "item")
	private List<Shelf> shelf;

	@JsonIgnore
	@OneToMany(mappedBy = "item")
	private List<Report> reports;
}
