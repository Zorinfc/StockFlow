package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dto.shelf.ShelfEditDTO;
import com.tobeto.entity.Shelf;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	public int addShelf(int count) {
		int shelfCount = (int) shelfRepository.count();
		// private static final
		int maxShelf = 10;
		if (maxShelf < shelfCount + count) {
			// count 40
			// max 50
			// scount 20
			count = maxShelf - shelfCount;
			// 0 döndüğünde exception olacak (max kapasiteye ulaşıldı gibi)
		}

		for (int i = 0; i < count; i++) {
			Shelf shelf = new Shelf();
			shelf.setNo(eksikSayilariBul());
			shelf.setCapacity(5);
			shelfRepository.save(shelf);
		}
		return count;
	}

	public Integer eksikSayilariBul() {
		List<Shelf> liste = shelfRepository.findTop50ByOrderByNoAsc();
		int check = 0;
		int count = 0;
		int rtr = 0;
//	        // listenin içindeki sayıları kontrol dizisine işaretle
//	        for (int num : liste) {
//	            kontrol[num] = true;
//	        }
		while (check == 0 && count < liste.size()) {
			if (liste.get(count).getNo() == count + 1) {
				count++;
				rtr = count + 1;
			} else {
				rtr = count + 1;
				check = 1;
			}
		}

		return rtr;
	}

	public void saveShelf(Shelf shelf) {
		shelfRepository.save(shelf);
	}

	public List<Shelf> getShelves() {
		return shelfRepository.findTop50ByOrderByNoAsc();
	}

	public Optional<Shelf> editShelf(ShelfEditDTO dto) {
		Optional<Shelf> shelf = shelfRepository.findByNo(dto.getNo());
		shelf.get().setQuantity(shelf.get().getQuantity() - dto.getQuantity());

		if (shelf.get().getQuantity() == 0) {
			shelf.get().setItem(null);
		}

		shelfRepository.save(shelf.get());
		return shelf;
	}

	public Shelf getEmptyShelf() {
		// İçinde item olmayan shelf return ediyor
		return getShelves().stream().filter(shelf -> shelf.getItem() == null).findFirst().get();
	}

	public int getNumbers() {
		return shelfRepository.findAll().size();
	}

	public boolean deleteShelf(int no) {
		Optional<Shelf> shelf = shelfRepository.findByNo(no);
		if (shelf.isPresent() && shelf.get().getQuantity() == 0) {
			shelfRepository.delete(shelf.get());
			return true;
		} else {
			return false;
		}
	}

	public Optional<Shelf> getShelfWithItem(int id) {
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

}
