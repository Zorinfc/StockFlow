package com.tobeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dto.item.AllItemsDTO;
import com.tobeto.entity.Item;
import com.tobeto.entity.Shelf;
import com.tobeto.repository.ItemRepository;
import com.tobeto.repository.ShelfRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private ShelfService shelfService;

	public Item addItem(Item item, int total) {
		Optional<Item> optItem = itemRepository.findByName(item.getName());
		if (optItem.isPresent()) {
			saveItemtoShelf(optItem.get(), total);
		} else {
			itemRepository.save(item);
			saveItemtoShelf(item, total);
		}
		return item;
	}

	// sorunsuz
	public List<Item> getItems() {
		return itemRepository.findAll();
	}

	// hata veren metod
	public List<AllItemsDTO> getAllItem() {
		List<Shelf> list = shelfService.getShelves();
		List<AllItemsDTO> response = new ArrayList<AllItemsDTO>();
		int toplam = 0;
		for (int i = 0; i < getItems().size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (getItems().get(i).getId() == list.get(j).getItem().getId()) {
					toplam += list.get(j).getItem_quantity();
				}
			}
			AllItemsDTO dto = new AllItemsDTO();
			dto.setTotal(toplam);
			dto.setMin_quantity(getItems().get(i).getMin_quantity());
			dto.setName(getItems().get(i).getName());
			response.add(dto);
			toplam = 0;
		}
		return response;
	}

	public Item getItem(int id) {
		return itemRepository.findById(id).get();
	}

	@Transactional
	public void saveItemtoShelf(Item item, int total) {
		Optional<Shelf> opt = shelfService.getShelvesWithItem(item.getId());
		if (opt.isPresent()) {
			Shelf shelf = opt.get();
			int count = total;
			int emptySpace = shelf.getCapacity() - shelf.getItem_quantity();
			if (count > emptySpace) {
				count = emptySpace;
			}
			shelf.setItem_quantity(shelf.getItem_quantity() + count);
			shelfRepository.save(shelf);
			total -= count;
		}
		if (total > 0) {
			fillEmptyShelf(total, item);
		}
	}

	private void fillEmptyShelf(int total, Item item) {
		while (total > 0) {
			int count = total;
			Shelf shelf = shelfService.getEmptyShelf();
			if (count > shelf.getCapacity()) {
				count = shelf.getCapacity();
			}
			shelf.setItem(item);
			shelf.setItem_quantity(count);
			shelfRepository.save(shelf);
			total -= count;
		}
	}

	@Transactional
	public Item deleteItem(Item item) {
		// silinecek item
		Optional<Item> opt = itemRepository.findByName(item.getName());

		List<Shelf> tempList = shelfRepository.findByItemId(opt.get().getId());
		for (int i = 0; i < tempList.size(); i++) {
			System.err.println(tempList.get(i));
			shelfRepository.deleteById(tempList.get(i).getId());
		}
		itemRepository.delete(opt.get());
		return item;
	}

//	public void deleteItemIfQuantityZero(Item item) {
//		Optional<Item> opt = itemRepository.findByName(item.getName());
//        if (opt.isPresent()) {
//            Item item = opt.get();
//            if (item.getMin_quantity() == 0) {
//                itemRepository.delete(item);
//            }
//        }
//    } 

}
