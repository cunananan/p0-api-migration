package com.revature.models;

import java.util.Objects;

public class Item {
	
	private int id;
	private String name;
	private String description;
	private int price;
	
	public Item() {
		super();
		id = -1;
	}

	public Item(String name, String description, int price) {
		this();
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	
	public Item(Item other) {
		super();
		setId(other.id);
		setName(other.name);
		setDescription(other.description);
		setPrice(other.price);
	}

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
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = Math.max(0, price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, price);
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
				&& price == other.price;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
}
