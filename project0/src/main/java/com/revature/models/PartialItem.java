package com.revature.models;

public class PartialItem {
	
	public int id;
	public String name;
	public String description;
	public int price;
	public int stock;
	
	public PartialItem() {
		super();
		id = -1;
		name = null;
		description = null;
		price = -1;
		stock = -1;
	}
	
	public void copyValidFieldsToItem(Item target) {
		if (id >= 0) target.setId(id);
		if (name != null) target.setName(name);
		if (description != null) target.setDescription(description);
		if (price >= 0) target.setPrice(price);
		if (stock >= 0) target.setStock(stock);
	}
}
