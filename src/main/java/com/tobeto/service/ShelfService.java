package com.tobeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.dto.shelf.ShelfEditDTO;
import com.tobeto.dto.shelf.ShelfResponseDTO;
import com.tobeto.entity.Shelf;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	// Shelf kapasitesi
	private static final int MAXSHELF = 50;

	// Shelf oluşturma
	public int addShelf(int count) {

		int shelfCount = (int) shelfRepository.count();

		if (MAXSHELF < shelfCount + count) {
			count = MAXSHELF - shelfCount;
		}

		for (int i = 0; i < count; i++) {
			Shelf shelf = new Shelf();
			shelf.setNo(eksikSayilariBul());
			shelf.setCapacity(5);
			shelfRepository.save(shelf);
		}
		return count;
	}

	// Kullanılmayan no değerini return eder
	public Integer eksikSayilariBul() {
		List<Shelf> liste = shelfRepository.findTop50ByOrderByNoAsc();
		int check = 0;
		int count = 0;
		int rtr = 1;
//		listenin içindeki sayıları kontrol dizisine işaretle
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

	// Shelf return ediyoruz ama shelf'te item yoksa null yerine isim olarak "empty"
	// return ediliyor
	// map işlemini aslında burada gerçekleştiriyoruz.
	public List<ShelfResponseDTO> getShelvesMapped() {

		List<Shelf> list = shelfRepository.findTop50ByOrderByNoAsc();
		List<ShelfResponseDTO> retList = new ArrayList<ShelfResponseDTO>();
		for (int i = 0; i < list.size(); i++) {
			ShelfResponseDTO dto = new ShelfResponseDTO();
			if (list.get(i).getItem() != null) {
				dto.setCapacity(list.get(i).getCapacity());
				dto.setItemName(list.get(i).getItem().getName());
				dto.setNo(list.get(i).getNo());
				dto.setQuantity(list.get(i).getQuantity());
				dto.setEmptySpace(list.get(i).getCapacity() - list.get(i).getQuantity());
			} else {
				dto.setCapacity(list.get(i).getCapacity());
				dto.setItemName("empty");
				dto.setNo(list.get(i).getNo());
				dto.setQuantity(list.get(i).getQuantity());
				dto.setEmptySpace(list.get(i).getCapacity() - list.get(i).getQuantity());
			}
			retList.add(dto);
		}
		return retList;
	}

	// Shelf edit
	public Optional<Shelf> editShelf(ShelfEditDTO dto) {

		Optional<Shelf> shelf = shelfRepository.findByNo(dto.getNo());
		shelf.get().setQuantity(shelf.get().getQuantity() - dto.getQuantity());
		if (shelf.get().getQuantity() == 0) {
			shelf.get().setItem(null);
		}
		shelfRepository.save(shelf.get());
		return shelf;
	}

	// İçinde item bulunmayan (item == null olan) shelf return eder
	public Shelf getEmptyShelf() {
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

	// Item id'sine sahip shelf return eder
	public Optional<Shelf> getShelfWithItem(int id) {
		List<Shelf> oShelf = shelfRepository.findByItemIdNotNull(id);
		Optional<Shelf> returnShelf = oShelf.stream().findFirst();
		return returnShelf;
	}
//
//	public int getEmptyShelfCount() {
//		List<Shelf> emptyShelf = getShelves().stream().filter(shelf -> shelf.getItem() == null).toList();
//		System.out.println(emptyShelf.size());
//		return emptyShelf.size();
//	}

}
