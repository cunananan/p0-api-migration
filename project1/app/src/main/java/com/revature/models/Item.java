package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Item {
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ItemCategory category;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String description;
	private int price;
	private int stock;
	
	public enum ItemCategory {
		NOT_SET,
		TOOL,
		CRAFTING_MATERIAL,
		UPGRADE_MATERIAL,
		KEY_ITEM,
		SPIRIT_ASH,
		ASH_OF_WAR,
		WEAPON,
		AMMUNITION,
		CATALYST,
		SPELL,
		ARMOR,
		TALISMAN,
		INFO
	}
	
	// Constructors
	public Item() {
		super();
		category = ItemCategory.NOT_SET;
		id = 0;
		name = "";
		description = "";
		price = 0;
		stock = 0;
	}
	public Item(ItemCategory category, int id, String name, String description, int price, int stock) {
		super();
		this.category = category;
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	// Setters and Getters
	public ItemCategory getCategory() {
		return category;
	}
	public void setCategory(ItemCategory category) {
		if (category != null && category != ItemCategory.NOT_SET) this.category = category;
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
		Item other = (Item) obj;
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
