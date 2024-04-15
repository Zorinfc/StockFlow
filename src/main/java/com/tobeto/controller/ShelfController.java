package com.tobeto.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.shelf.ShelfCreateDTO;
import com.tobeto.dto.shelf.ShelfDeleteRequestDTO;
import com.tobeto.dto.shelf.ShelfResponseDTO;
import com.tobeto.service.ShelfService;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelfController {

	@Autowired
	private ShelfService shelfService;
	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@GetMapping()
	public List<ShelfResponseDTO> getShelves() {

		List<ShelfResponseDTO> response = shelfService.getShelves().stream()
				.map(s -> responseMapper.map(s, ShelfResponseDTO.class)).toList();

		return response;
	}

	@PostMapping("/add")
	public String addShelf(@RequestBody ShelfCreateDTO dto) {
		int count = shelfService.addShelf(dto.getCount());
		return String.valueOf(count);
	}

	@PostMapping("/delete")
	public boolean deleteShelf(@RequestBody ShelfDeleteRequestDTO dto) {
//		System.err.println(dto);
		return shelfService.deleteShelf(dto.getNo());
	}

//	@GetMapping("/test")
//	public List<Shelf> test(@RequestBody ShelfDeleteRequestDTO dto) {
//		return shelfService.getShelfWithItem(dto.getNo());
//	}

//	@GetMapping("/deneme")
//	public Optional<Shelf> deneme(@RequestBody ShelfDeleteRequestDTO dto) {
//		return shelfService.test(dto.getNo());
//	}

//	@GetMapping("/deneme")
//	public List<Shelf> deneme(@RequestBody String name) {
//		return shelfService.testShelfFunction(name);
//	}
}
