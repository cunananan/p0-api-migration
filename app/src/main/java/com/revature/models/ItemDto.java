package com.revature.models;

import java.util.Objects;

import com.revature.models.Item.ItemCategory;

public class ItemDto {
	
	public ItemCategory category;
	public int id;
	public String name;
	public String description;
	public int price;
	public int stock;
	
	public ItemDto() {
		super();
		category = null;
		id = -1;
		name = null;
		description = null;
		price = -1;
		stock = -1;
	}
	
	public ItemDto(Item source) {
		super();
		copyFrom(source);
	}
	
	public void copyFrom(Item source) {
		category = source.getCategory();
		id = source.getId();
		name = source.getName();
		description = source.getDescription();
		price = source.getPrice();
		stock = source.getStock();
	}
	
	public void copyTo(Item target) {
		if (category != null) target.setCategory(category); 
		if (id >= 0) target.setId(id);
		if (name != null) target.setName(name);
		if (description != null) target.setDescription(description);
		if (price >= 0) target.setPrice(price);
		if (stock >= 0) target.setStock(stock);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(category, description, id, name, price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDto other = (ItemDto) obj;
		return category == other.category && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(name, other.name) && price == other.price && stock == other.stock;
	}

	@Override
	public String toString() {
		return "Item [category=" + category + ", id=" + id + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", stock=" + stock + "]";
	}
	protected String toStringPartial() {
		return "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", stock=" + stock;
	}
}
