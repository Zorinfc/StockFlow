package com.tobeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.item.ItemCreateDTO;
import com.tobeto.entity.Item;
import com.tobeto.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@PostMapping("/add")
	public String addItem(@RequestBody ItemCreateDTO dto) {
		Item item = new Item();
		item.setMin_quantity(dto.getMin_quantity());
		item.setName(dto.getName());
		itemService.addItem(item, dto.getTotal());
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
