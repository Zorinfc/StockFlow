package com.tobeto.dto.user;

import com.tobeto.entity.Role;

import lombok.Data;

@Data
public class UserResponseDTO {

	private String email;
	private String name;
	private String lastName;
	private Role role;
}
