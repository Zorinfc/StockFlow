package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (itemRepository.findByName(item.getName()).isPresent()) {
			saveItemtoShelf(item, total);
		} else {
			itemRepository.save(item);
			saveItemtoShelf(item, total);
		}
		return item;
	}

	public List<Item> getItems() {
		return itemRepository.findAll();
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
			Shelf shelf = shelfService.getEmptyShelf();
			System.out.println(shelf);

		}
	}

//	@Transactional
//	public void saveItemtoShelf(Item item, int total) {
//		// getShelfWithItem null geliyor..
//		while (total > 0) {
//			List<Shelf> list = shelfService.getShelvesWithItem(item.getId()).get();
//			int listSize = list.size();
//			// item idli yer olan shelf var mı
//			if (listSize != 0) {
//				for (int i = 0; i < list.size(); i++) {
//					list.get(i).setItem_quantity(list.get(i).getCapacity() - list.get(i).getItem_quantity());
//					total -= list.get(i).getCapacity() - list.get(i).getItem_quantity();
//					listSize--;
//				}
//			} else if (list.isEmpty()) {
//				for (int i = 0; i < shelfService.getEmptyShelfCount(); i++) {
//					if (total - 5 >= 0) {
//						Shelf shelf = shelfService.getEmptyShelf();
//						shelf.setItem(item);
//						shelf.setItem_quantity(5);
//						shelfService.saveShelf(shelf);
//						total -= 5;
//					} else {
//						Shelf shelf = shelfService.getEmptyShelf();
//						shelf.setItem(item);
//						shelf.setItem_quantity(total);
//						System.out.println(total);
//						shelfService.saveShelf(shelf);
//						total = 0;
//					}
//				}
//			} else {
//				// yer yok exception ()
//				System.out.println("boş shelf yok");
//			}
//		}
//
//	}

}
