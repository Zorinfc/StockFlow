package com.tobeto.dto.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfResponseDTO {

	private int no;
	private int capacity;
	private int quantity;
	private int emptySpace;
	private String itemName;
}
