package com.tobeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	Optional<Item> findByName(String name);

}
