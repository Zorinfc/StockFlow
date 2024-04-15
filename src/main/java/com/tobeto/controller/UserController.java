package com.tobeto.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.user.UserDTO;
import com.tobeto.dto.user.UserResponseDTO;
import com.tobeto.entity.User;
import com.tobeto.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody UserDTO dto) {
		User user = requestMapper.map(dto, User.class);
		return ResponseEntity.ok().body(userService.createUser(user));
	}

	@GetMapping()
	public List<UserResponseDTO> getUsers() {
		List<User> list = userService.getUsers();
		List<UserResponseDTO> dto = new ArrayList<UserResponseDTO>();
		list.forEach(u -> {
			dto.add(responseMapper.map(u, UserResponseDTO.class));
		});
		return dto;
	}

	@DeleteMapping("/delete")
	public void deleteUser(@RequestBody User user) {
		userService.deleteUser(user);
	}

	@PutMapping("/update")
	public void updateUser(@RequestBody UserDTO dto) {
		User user = requestMapper.map(dto, User.class);
		userService.updateUser(user);
	}

}
