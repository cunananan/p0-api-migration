package com.revature.persistence;

import java.util.List;
import com.revature.models.Item;
import com.revature.models.Spell;


public interface SpellDao {
	
	// Gets list of all spells available
	public List<Spell> getSpells();
	// Gets spell of id from collection; returns null otherwise
	public Spell getSpell(int id);
	// Adds spell to collection; returns id of spell if successful, else returns -1
	public int addSpell(Spell spell);
	// Deletes spell of id; returns true iff successful
	public boolean deleteSpell(int id);
	// Updates spell given; returns true iff successful
	public boolean updateSpell(Spell spell);
}
