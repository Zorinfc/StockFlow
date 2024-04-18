package com.tobeto.dto.user;

import lombok.Data;

@Data
public class UpdateResponseDTO {

	private String email;
	private String name;
	private String lastName;
	private int roleId;
}
