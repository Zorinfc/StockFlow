package com.tobeto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.user.UpdateResponseDTO;
import com.tobeto.dto.user.UserDTO;
import com.tobeto.dto.user.UserRequestDTO;
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
	public boolean createUser(@RequestBody UserDTO dto) {
		return userService.createUser(dto);
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

	@GetMapping("/get")
	public UpdateResponseDTO getUser(@RequestParam String email) {
		Optional<User> user = userService.getUserByEmail(email);
		UpdateResponseDTO response = requestMapper.map(user.get(), UpdateResponseDTO.class);
//		System.err.println(email);
		return response;
	}

	@PostMapping("/delete")
	public boolean deleteUser(@RequestBody UserRequestDTO dto) {
		return userService.deleteUser(dto);
	}

	@PostMapping("/update")
	public UserResponseDTO updateUser(@RequestBody UserDTO dto) {
		User user = userService.updateUser(dto);
		UserResponseDTO response = requestMapper.map(user, UserResponseDTO.class);
		return response;
	}

}
