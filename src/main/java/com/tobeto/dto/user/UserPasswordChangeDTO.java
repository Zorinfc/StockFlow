package com.tobeto.dto.user;

import lombok.Data;

@Data
public class UserPasswordChangeDTO {

	private String userEmail;
	private String password;
	private String newPassword;
}
