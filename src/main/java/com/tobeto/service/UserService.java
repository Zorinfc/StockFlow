package com.tobeto.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.entity.User;
import com.tobeto.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;

	// create
	// duzenlenecek (eklenmek istenene personel var mı gibi)
	// sifre encode edilecek
	@Transactional
	public User createUser(User user) {
		User tempUser = new User();
		tempUser.setEmail(user.getEmail());
		tempUser.setPassword(encoder.encode(user.getPassword()));
		tempUser.setName(user.getName());
		tempUser.setLastName(user.getLastName());
		tempUser.setRole(user.getRole());
		return userRepository.save(tempUser);
	}

	// delete
	public void deleteUser(User user) {

		Optional<User> optUser = userRepository.findById(user.getId());
		if (optUser.isPresent()) {
			userRepository.delete(user);
		} else {
			System.err.println("user not found");
		}
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(UUID id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// update ( sifre degisikligi)
	public User updateUser(UUID id, User user) {

		Optional<User> optUser = userRepository.findById(id);

		if (optUser.isPresent()) {
			optUser.get().setEmail(user.getEmail());
			optUser.get().setName(user.getName());
			optUser.get().setLastName(user.getLastName());
			optUser.get().setPassword(user.getPassword());
			optUser.get().setRole(user.getRole());
			return userRepository.save(optUser.get());

		} else {
			System.err.println("user not found");
			return null;
		}

	}

}
