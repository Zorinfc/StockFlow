package com.tobeto.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {

	private String email;
	private String name;
	private String lastName;
	private String roleName;
	private boolean active;
}
