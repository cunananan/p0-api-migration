package com.revature.services;

import java.util.List;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.PartialSpell;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.persistence.SpellDao;
import com.revature.persistence.SpellPostgres;

public class SpellService {
	
	private SpellDao sd;
	
	public SpellService() {
		sd = new SpellPostgres();
	}
	// Used only for testing purposes
	public SpellService(SpellDao sd) {
		this.sd = sd;
	}
	
	public List<Spell> getSpells() throws ItemNotFoundException {
		List<Spell> spells = sd.getSpells();
		if (spells == null || spells.isEmpty()) {
			throw new ItemNotFoundException();
		}
		return spells;
	}
	
	public List<Spell> getSpells(SpellType type, int priceCap, Boolean inStock,
            int intCap, int faiCap, int arcCap) throws ItemNotFoundException
	{
		List<Spell> spells = 
				sd.getSpells(type, priceCap, inStock, intCap, faiCap, arcCap);
		
		if (spells == null || spells.isEmpty()) {
			throw new ItemNotFoundException();
		}
		return spells;
	}
	
	public Spell getSpell(int id) throws ItemNotFoundException {
		Spell spell = sd.getSpell(id);
		if (spell == null) {
			throw new ItemNotFoundException();
		}
		return spell;
	}
	
	public int addSpell(Spell spell) throws InsertionFailureException {
		int id = sd.appendSpell(spell);
		if (id < 0) {
			throw new InsertionFailureException();
		}
		return id;
	}
	
	public Spell deleteSpell(int id) throws ItemNotFoundException {
		Spell result = sd.deleteSpell(id); 
		if (result == null) {
			throw new ItemNotFoundException();
		}
		return result;
	}
	
	public Spell updateSpell(PartialSpell spellChanges) throws ItemNotFoundException {
		Spell spell = null;
		if (spellChanges != null) spell = sd.getSpell(spellChanges.id);
		if (spell == null) {
			throw new ItemNotFoundException();
		}
		spellChanges.copyValidFieldsToItem(spell);
		if (sd.updateSpell(spell) == null) {
			throw new ItemNotFoundException();
		}
		return spell;
	}
}




