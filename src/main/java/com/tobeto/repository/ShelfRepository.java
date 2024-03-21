package com.tobeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

// çalışmadı
//	@Query("SELECT s FROM Shelf s JOIN s.item i WHERE i.name=?1")
//	List<Shelf> findByItemName(String name);

//	public int countById();

//	@Query("SELECT b FROM Box b WHERE b.fruit.id = :fruitId and b.count < b.capacity")
//	Optional<Box> findByFruitIdNotFull(int fruitId);

	@Query("SELECT s FROM Shelf s WHERE s.item.id = :itemId and s.item_quantity < s.capacity")
	Optional<Shelf> findByItemIdNotNull(int itemId);

//	List<Shelf> findAllByItemQuantity(int quantity);
}
