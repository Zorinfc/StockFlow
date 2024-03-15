package com.tobeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.shelf.ShelfRequestDTO;
import com.tobeto.entity.Shelf;
import com.tobeto.service.ShelfService;

@RestController
@RequestMapping("/shelf")
public class ShelfController {

	@Autowired
	private ShelfService shelfService;

	@GetMapping()
	public List<Shelf> getShelves() {
//		System.out.println("getShelves.size()==>" + shelfService.getShelves().size());
		return shelfService.getShelves();
	}

	@PostMapping("/add")
	public String addShelf(@RequestBody ShelfRequestDTO dto) {
		int count = shelfService.addShelf(dto.getCount());
		return String.valueOf(count);
	}
}
