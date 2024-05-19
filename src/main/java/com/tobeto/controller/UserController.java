package com.tobeto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.ResponseMsgDTO;
import com.tobeto.dto.user.UpdateResponseDTO;
import com.tobeto.dto.user.UserDTO;
import com.tobeto.dto.user.UserPasswordChangeDTO;
import com.tobeto.dto.user.UserRequestDTO;
import com.tobeto.dto.user.UserResponseDTO;
import com.tobeto.entity.User;
import com.tobeto.repository.UserRepository;
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

	@Autowired
	private UserRepository repo;

	@PostMapping("/test")
	public void test(@RequestBody UserRequestDTO dto) {
		Optional<User> user = userService.getUserByEmail(dto.getEmail());
		user.get().setActive(true);
		repo.save(user.get());
	}

	@GetMapping()
	public List<UserResponseDTO> getUsers() {
		List<User> list = userService.getUsers();
		List<UserResponseDTO> dto = new ArrayList<UserResponseDTO>();
		list.stream().filter(User::isActive).forEach(u -> dto.add(responseMapper.map(u, UserResponseDTO.class)));
		return dto;
	}

	@GetMapping("/get")
	public UpdateResponseDTO getUser(@RequestParam String email) {
		Optional<User> user = userService.getUserByEmail(email);
		UpdateResponseDTO response = requestMapper.map(user.get(), UpdateResponseDTO.class);
		return response;
	}

	@PostMapping("/delete")
	public boolean deactiveUser(@RequestBody UserRequestDTO dto) {
		return userService.inactiveUser(dto);
	}

	@PostMapping("/update")
	public UserResponseDTO updateUser(@RequestBody UserDTO dto) {
		User user = userService.updateUser(dto);
		UserResponseDTO response = requestMapper.map(user, UserResponseDTO.class);
		return response;
	}

	@PostMapping("/password")
	public ResponseEntity<ResponseMsgDTO> passwordChange(@RequestBody UserPasswordChangeDTO dto) {
		boolean value = userService.changePassword(dto);
		if (value) {
			return ResponseEntity.ok().body(new ResponseMsgDTO("Sifre Degistirildi"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMsgDTO("Hatali sifre girildi"));
		}

	}

}
