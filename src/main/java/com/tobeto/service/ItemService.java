package com.tobeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dto.item.AllItemsDTO;
import com.tobeto.dto.item.ItemInOutDTO;
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

	// Item ismini önce kontrol ediyor
	@Transactional
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

	public List<Item> getItems() {
		return itemRepository.findAll();
	}

	public List<AllItemsDTO> getAllItem() {
		List<Shelf> list = shelfService.getShelves();
		List<AllItemsDTO> response = new ArrayList<AllItemsDTO>();
		int toplam = 0;
		for (int i = 0; i < getItems().size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getItem() != null && getItems().get(i).getId() == list.get(j).getItem().getId()) {
					toplam += list.get(j).getQuantity();
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

	public Optional<Item> getItemByName(String name) {
		return itemRepository.findByName(name);
	}

	// girişi yapılan item'ı shelflere dağıtıyor
	@Transactional
	public void saveItemtoShelf(Item item, int total) {
		int value = 0;
		while (value == 0) {
			Optional<Shelf> opt = shelfService.getShelfWithItem(item.getId());
			if (opt.isPresent()) {
				Shelf shelf = opt.get();
				int count = total;
				int emptySpace = shelf.getCapacity() - shelf.getQuantity();
				if (count > emptySpace) {
					count = emptySpace;
				}
				shelf.setQuantity(shelf.getQuantity() + count);
				shelfRepository.save(shelf);
				total -= count;
			} else {
				value = 1;
			}
		}
		if (total > 0) {
			fillEmptyShelf(total, item);
		}

	}

	// içerisinde item olmayan shelf'e item yerleştirme
	private void fillEmptyShelf(int total, Item item) {
		while (total > 0) {
			int count = total;
			Shelf shelf = shelfService.getEmptyShelf();
			if (count > shelf.getCapacity()) {
				count = shelf.getCapacity();
			}
			shelf.setItem(item);
			shelf.setQuantity(count);
			shelfRepository.save(shelf);
			total -= count;
		}
	}

	// item silme, shelflerdeki itemları da null yapıyoruz
	@Transactional
	public boolean deleteItem(Item item) {
		// silinecek item
		Optional<Item> opt = getItemByName(item.getName());
		boolean returnValue = false;
		// System.out.println(opt);
		List<Shelf> tempList = shelfRepository.findByItemId(opt.get().getId());
		if (opt.isPresent()) {

			for (int i = 0; i < tempList.size(); i++) {
				// System.err.println(tempList.get(i));
				tempList.get(i).setQuantity(0);
				tempList.get(i).setItem(null);
				shelfRepository.save(tempList.get(i));
			}
			itemRepository.delete(opt.get());
			returnValue = true;
		}
		return returnValue;
	}

	// Sistemde olan item'ı ekleme çıkarma işlemi
	public void itemInOut(ItemInOutDTO dto) {

		Optional<Item> opItem = itemRepository.findByName(dto.getName());
		int count = dto.getCount();
		// System.out.println(dto.isOperator());
		if (opItem.isPresent()) {
			// kapasite kontrolu eklenecek
			// true ise toplama işlemi
			if (dto.isOperator()) {
				addItem(opItem.get(), dto.getCount());
			}
			// false ise çıkarma işlemi yapılıyor
			else {
				List<Shelf> list = shelfRepository.findTop50ByOrderByNoDesc();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getItem() != null && dto.getName().equals(list.get(i).getItem().getName())) {
//						System.err.println(i);

						if (list.get(i).getQuantity() <= count) {
							count = count - list.get(i).getQuantity();
							list.get(i).setItem(null);
							list.get(i).setQuantity(0);
							shelfRepository.save(list.get(i));
							System.err.println(count);
						} else {
							list.get(i).setQuantity(list.get(i).getQuantity() - count);
							count = 0;
							shelfRepository.save(list.get(i));
						}

					}
				}
			}
		}
	}

}
