package com.tobeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.entity.Item;
import com.tobeto.service.ItemService;
import com.tobeto.service.ShelfService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ShelfService shelfService;

	@PostMapping("/add")
	public String addItem() {

		return "";
	}

	@GetMapping()
	public List<Item> getItems() {
		return itemService.getItems();
	}

	@GetMapping("/test")
	public String testFunc() {
		return "";
	}
}
