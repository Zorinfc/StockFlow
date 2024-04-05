package com.tobeto.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.ResponseMsgDTO;
import com.tobeto.dto.item.AllItemsDTO;
import com.tobeto.dto.item.ItemCreateDTO;
import com.tobeto.dto.item.ItemInOutDTO;
import com.tobeto.dto.item.ItemRequestDTO;
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

	// CRUD

	@PostMapping("/add")
	public Item addItem(@RequestBody ItemCreateDTO dto) {
		Item item = new Item();
		item.setMin_quantity(dto.getMin_quantity());
		item.setName(dto.getName());
		return itemService.addItem(item, dto.getQuantity());
	}

	@GetMapping()
	public List<AllItemsDTO> getItems() {
		return itemService.getAllItem();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseMsgDTO> deleteItem(@RequestBody ItemRequestDTO dto) {
		Item item = requestMapper.map(dto, Item.class);
		itemService.deleteItem(item);
		return ResponseEntity.ok(new ResponseMsgDTO(item.getName() + " Deleted"));
	}

	@PostMapping("/opt")
	public ResponseEntity<ResponseMsgDTO> inOutItem(@RequestBody ItemInOutDTO dto) {
		itemService.operation(dto);
		return ResponseEntity.ok(new ResponseMsgDTO(dto.getName()));
	}

//	@GetMapping("/test")
//	public List<Item> test() {
//		return itemService.getItems();
//	}

}
