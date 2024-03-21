package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Shelf;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	public int addShelf(int count) {
		int shelfCount = (int) shelfRepository.count();
		int maxShelf = 10;
		if (maxShelf < shelfCount + count) {
			// count 40
			// max 50
			// scount 20
			count = maxShelf - shelfCount;
			// 0 döndüğünde exception olacak (max kapasiteye ulaşıldı gibi)
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

	public void saveShelf(Shelf shelf) {
		shelfRepository.save(shelf);
	}

	public List<Shelf> getShelves() {
		return shelfRepository.findAll();
	}

	public Shelf getEmptyShelf() {
		// İçinde item olmayan shelf return ediyor
		return getShelves().stream().filter(shelf -> shelf.getItem() == null).findFirst().get();
	}

	public int getNumbers() {
		return shelfRepository.findAll().size();
	}

	public void deleteShelf(int id) {
		Optional<Shelf> shelf = shelfRepository.findById(id);
		if (shelf.isPresent()) {
			shelfRepository.delete(shelf.get());

		} else {
			// exception
		}
	}

	public Optional<Shelf> getShelvesWithItem(int id) {
//		System.out.println("no==>" + id);

		Optional<Shelf> oShelf = shelfRepository.findByItemIdNotNull(id);
//		List<Shelf> list = new ArrayList<Shelf>();
//		getShelves().stream().forEach(s -> {
//			if (s.getItem().getId() == id && s.getItem_quantity() < s.getCapacity()) {
//				list.add(s);
//				System.out.println("list add");
//			} else {
//				System.out.println("Boş shelf bulunamadı");
//			}
//		});
		return oShelf;
	}

	public int getEmptyShelfCount() {
		List<Shelf> emptyShelf = getShelves().stream().filter(shelf -> shelf.getItem() == null).toList();
		System.out.println(emptyShelf.size());
		return emptyShelf.size();
	}

//	public Optional<Shelf> test(int id) {
//		return shelfRepository.findByItemIdNotNull(id);
//	}
}
