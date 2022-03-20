package com.revature.services;

import java.util.List;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.Spell;
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
	
	public Spell getSpell(int id) throws ItemNotFoundException {
		Spell spell = sd.getSpell(id);
		if (spell == null) {
			throw new ItemNotFoundException();
		}
		return spell;
	}
	
	public int addSpell(Spell spell) throws InsertionFailureException {
		if (spell != null) spell.verifyFields();
		int id = sd.appendSpell(spell);
		if (id < 0) {
			throw new InsertionFailureException();
		}
		return id;
	}
	
	public void deleteSpell(int id) throws ItemNotFoundException {
		if (!sd.deleteSpell(id)) {
			throw new ItemNotFoundException();
		}
	}
	
	public void updateSpell(Spell spellChanges) throws ItemNotFoundException {
		Spell spell = sd.getSpell(spellChanges.getId());
		if (spell == null) {
			throw new ItemNotFoundException();
		}
		spell.copyFrom(spellChanges);
		if (!sd.updateSpell(spell)) {
			throw new ItemNotFoundException();
		}
	}
}




