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

	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	public List<Item> getItems() {
		return itemRepository.findAll();
	}
}
