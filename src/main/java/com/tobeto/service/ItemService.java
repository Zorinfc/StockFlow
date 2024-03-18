package com.tobeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Item;
import com.tobeto.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ShelfService shelfService;

	public Item addItem(Item item, int total) {
		itemRepository.save(item);
		shelfService.saveShelf(item, total);

		return item;
	}

	public List<Item> getItems() {
		return itemRepository.findAll();
	}

	public Item getItem(int id) {
		return itemRepository.findById(id).get();
	}
}
