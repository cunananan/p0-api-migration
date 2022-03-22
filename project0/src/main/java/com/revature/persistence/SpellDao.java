package com.revature.persistence;

import java.util.List;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;


public interface SpellDao {
	
	// Gets list of all spells available
	public List<Spell> getSpells();
	// Gets list of all spells the satisfy all the queries passed;
	// null/negative params will be ignored
	public List<Spell> getSpells(SpellType type, int priceCap, Boolean inStock,
	                             int intCap, int faiCap, int arcCap);
	// Gets spell of id from collection; returns null otherwise
	public Spell getSpell(int id);
	// Adds spell the index of its id; returns id of spell if successful, else returns -1
	public int insertSpell(Spell spell);
	// Adds spell to end of collection; returns id of spell if successful, else returns -1
	public int appendSpell(Spell spell);
	// Deletes spell of id; returns deleted spell if successful, else returns null
	public Spell deleteSpell(int id);
	// Updates spell given; returns updated spell if successful, else returns null
	public Spell updateSpell(Spell spell);
}
