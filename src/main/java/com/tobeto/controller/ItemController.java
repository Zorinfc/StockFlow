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

import com.tobeto.dto.item.AllItemsDTO;
import com.tobeto.dto.item.ItemCreateDTO;
import com.tobeto.entity.Item;
import com.tobeto.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@PostMapping("/add")
	public Item addItem(@RequestBody ItemCreateDTO dto) {
		Item item = new Item();
		item.setMin_quantity(dto.getMin_quantity());
		item.setName(dto.getName());
		return itemService.addItem(item, dto.getItem_quantity());
	}

//	@GetMapping()
//	public List<Item> getItems() {
//		return itemService.getItems();
//	}

	@GetMapping()
	public List<AllItemsDTO> testFunc() {
		return itemService.getAllItem();
	}

}
