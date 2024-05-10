package com.tobeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

	@Query("SELECT s FROM Shelf s WHERE s.item.id = :itemId and s.quantity < s.capacity")
	List<Shelf> findByItemIdNotNull(int itemId);

	@Query("SELECT s FROM Shelf s WHERE s.item.id = :itemId")
	List<Shelf> findByItemId(int itemId);

	List<Shelf> findTop50ByOrderByNoAsc();

	List<Shelf> findTop50ByOrderByNoDesc();

	Optional<Shelf> findByNo(int no);

}
