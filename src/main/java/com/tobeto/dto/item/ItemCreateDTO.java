package com.tobeto.dto.item;

import lombok.Data;

@Data
public class ItemCreateDTO {

	private String name;
	private int min_quantity;
	private int quantity;

}
