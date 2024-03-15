package com.tobeto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Shelf;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository repository;

	public int addShelf(int count) {
		if (count > 50) {
			count = 50;
		}
		Shelf shelf = new Shelf();
		for (int i = 0; i < count; i++) {

			if (getNumbers().get(i).equals(null)) {
				shelf.setCapacity(5);
				shelf.setNo(i + 1);
				repository.save(shelf);
			}

			else if (getNumbers().get(i) == i + 1) {
				System.out.println("else if içi");
			} else {
				shelf.setCapacity(5);
				shelf.setNo(i + 1);
				repository.save(shelf);
				System.out.println("else");

			}

		}
		return count;
	}

	public List<Shelf> getShelves() {
		System.out.println(getNumbers());
		return repository.findAll();
	}

	public List<Integer> getNumbers() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < repository.findAll().size(); i++) {
			list.add(repository.findAll().get(i).getNo());
//			System.out.println("i==> " + i);
		}

//		Stream<Shelf> stream = getShelves().stream();
//		stream.forEach(a -> list.add(a.getNo()));
//		getShelves().forEach(a -> list.add(a.getNo()));
		return list;
	}
}
