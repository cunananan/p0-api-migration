package com.revature.persistence;

import java.util.List;
import com.revature.models.Item;


public interface ItemDao {
	
	// Gets list of all items available
	public List<Item> getItems();
	// Gets item of itemId from collection; returns null otherwise
	public Item getItem(int itemId);
	// Adds newItem to collection; returns id of newItem if successful, else returns -1
	public int addItem(Item newItem);
	// Deletes item of itemId; returns true iff successful
	public boolean deleteItem(int itemId);
	// Replaces item of itemId with newItem; returns true iff successful
	public boolean updateItem(int itemId, Item newItem);
}
