package com.tobeto.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true)
	private String email;
	private String password;

	private String name;
	private String lastName;
	private boolean active;

	@ManyToOne
	private Role role;

	@OneToMany(mappedBy = "user")
	private List<Report> reports;
}
