package com.revature.models;

import java.util.Objects;

public class Item {
	
	private int id;
	private String name;
	private String description;
	private int price;
	private int stock;
	
	// Constructors
	public Item() {
		super();
		id = 0;
		name = "";
		description = "";
		price = 0;
		stock = 0;
	}
	public Item(String name, String description, int price) {
		super();
		id = 0;
		setName(name);
		setDescription(description);
		setPrice(price);
		stock = 0;
	}
	public Item(int id, String name, String description, int price, int stock) {
		this(name, description, price);
		setId(id);
		setName(name);
		setDescription(description);
		setPrice(price);
		setStock(stock);
	}
	public Item(Item other) {
		super();
		setId(other.id);
		setName(other.name);
		setDescription(other.description);
		setPrice(other.price);
		setStock(other.stock);
	}

	// Setters and Getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = Math.max(0, id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = (name == null) ? "" : name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = (description == null) ? "" : description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = Math.max(0, price);
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = Math.max(0, stock);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
				&& price == other.price && stock == other.stock;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock=" + stock + "]";
	}
	protected String toStringPartial() {
		return "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock=" + stock;
	}
}
