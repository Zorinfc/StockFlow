package com.tobeto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.account.LoginRequestDTO;
import com.tobeto.entity.User;
import com.tobeto.service.TokenService;
import com.tobeto.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
		Optional<User> optUser = userService.getUserByEmail(dto.getEmail());
		if (optUser.isPresent() && encoder.matches(dto.getPassword(), optUser.get().getPassword())) {
			String token = tokenService.createToken(optUser.get());
			return ResponseEntity.ok(token);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
