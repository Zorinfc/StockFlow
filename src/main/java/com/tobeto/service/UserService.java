package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.dto.user.UserDTO;
import com.tobeto.dto.user.UserPasswordChangeDTO;
import com.tobeto.dto.user.UserRequestDTO;
import com.tobeto.entity.Role;
import com.tobeto.entity.User;
import com.tobeto.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder encoder;

	// create
	@Transactional
	public boolean createUser(UserDTO user) {
		boolean returnValue = false;
		Optional<User> check = userRepository.findByEmail(user.getEmail());
		User tempUser = new User();
		Role role = roleService.findById(user.getRoleId()).get();
		if (!check.isPresent()) {
			tempUser.setEmail(user.getEmail());
			tempUser.setPassword(encoder.encode(user.getPassword()));
			tempUser.setName(user.getName());
			tempUser.setLastName(user.getLastName());
			tempUser.setRole(role);
			tempUser.setActive(true);
			userRepository.save(tempUser);
			returnValue = true;
		}

		return returnValue;
	}

	// delete
	public boolean deleteUser(UserRequestDTO user) {
		boolean returnValue = false;
		Optional<User> optUser = userRepository.findByEmail(user.getEmail());
		if (optUser.isPresent()) {
			userRepository.delete(optUser.get());
			returnValue = true;
		}
		return returnValue;
	}

	public boolean inactiveUser(UserRequestDTO user) {
		boolean retVal = false;
		Optional<User> oUser = userRepository.findByEmail(user.getEmail());
		if (oUser.isPresent()) {
			oUser.get().setActive(false);
			userRepository.save(oUser.get());
		}
		return retVal;
	}

	// Return user list

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// update user
	public User updateUser(UserDTO dto) {
		Optional<User> optUser = getUserByEmail(dto.getEmail());
		if (optUser.isPresent()) {
			if (optUser.get().getPassword() == "") {
				optUser.get().setName(dto.getName());
				optUser.get().setLastName(dto.getLastName());
				optUser.get().setRole(roleService.findById(dto.getRoleId()).get());
			} else {
				optUser.get().setName(dto.getName());
				optUser.get().setLastName(dto.getLastName());
				optUser.get().setRole(roleService.findById(dto.getRoleId()).get());
				optUser.get().setPassword(encoder.encode(dto.getPassword()));
			}
			return userRepository.save(optUser.get());
		} else {
			System.err.println("user not found");
			return null;
		}
	}

	// şifre değişikliği
	public boolean changePassword(UserPasswordChangeDTO dto) {
		boolean retVal = false;
		Optional<User> optUser = getUserByEmail(dto.getUserEmail());
		if (optUser.isPresent() && encoder.matches(dto.getPassword(), optUser.get().getPassword())) {
			optUser.get().setPassword(encoder.encode(dto.getNewPassword()));
			userRepository.save(optUser.get());
			retVal = true;
		}
		return retVal;
	}
}
