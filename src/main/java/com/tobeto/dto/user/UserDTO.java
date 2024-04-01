package com.tobeto.dto.user;

import com.tobeto.entity.Role;

import lombok.Data;

@Data
public class UserDTO {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private Role role;
}
