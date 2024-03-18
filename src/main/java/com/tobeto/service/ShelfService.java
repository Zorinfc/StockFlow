package com.tobeto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Item;
import com.tobeto.entity.Shelf;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	public int addShelf(int count) {
		if (count > 50) {
			count = 50;
		}
		for (int i = 0; i < count; i++) {
			if (shelfRepository.findAll().get(getNumbers() - 1)
					.getNo() == shelfRepository.findAll().get(getNumbers() - 2).getNo() + 1) {
				Shelf shelf = new Shelf();
				shelf.setCapacity(5);
				shelf.setNo(shelfRepository.findAll().get(getNumbers() - 1).getNo() + 1);
				shelfRepository.save(shelf);
			}
		}
		return count;
	}

	public List<Shelf> getShelves() {
		// System.out.println(getNumbers());
		return shelfRepository.findAll();
	}

	public int getNumbers() {

		return shelfRepository.findAll().size();

//		List<Integer> list = new ArrayList<>();
//		for (int i = 0; i < repository.findAll().size(); i++) {
//			list.add(repository.findAll().get(i).getNo());
//			System.out.println("i==> " + i);
//		}
//		Stream<Shelf> stream = getShelves().stream();
//		stream.forEach(a -> list.add(a.getNo()));
//		getShelves().forEach(a -> list.add(a.getNo()));
//		return list;
	}

	public void deleteShelf(int no) {
		Shelf shelf = shelfRepository.findByNo(no).orElseThrow();
		shelfRepository.delete(shelf);
	}

	// İçinde item olmayan shelf return ediyor
	public Shelf getEmptyShelf() {
		return getShelves().stream().filter(shelf -> shelf.getItem_quantity() == 0).findFirst().get();
	}

	public List<Shelf> getShelfWithItem(int id) {
		System.out.println("id==>" + id);
		List<Shelf> list = new ArrayList<Shelf>();
		getShelves().stream().forEach(s -> {
			if (s.getItem().getId() == id && s.getItem_quantity() < s.getCapacity()) {
				list.add(s);
				System.out.println("list add");
			} else {
				System.out.println("Boş shelf bulunamadı");
			}
		});
		return list;
	}

	public void saveShelf(Item item, int total) {

		if (getShelfWithItem(item.getId()).isEmpty()) {
			Shelf shelf = new Shelf();
			while (total != 0) {
				shelf.setItem(item);
				shelf.setCapacity(5);
				shelf.setItem_quantity(5);
				total -= 5;
				shelfRepository.save(shelf);
			}
		}

	}

}
