package com.revature.persistence;

import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;

public class TestSpellPostgres {
	public static final SpellDao sd = new SpellPostgres();
	
	public static void main(String[] args) {
		
		// Test getSpells()
		System.out.println("Full Spell List:");
		System.out.println(sd.getSpells());
		System.out.println();
		
		// Test getSpell(id)
		System.out.println("Spell #1: ");
		System.out.println(sd.getSpell(1));
		System.out.println("Spell #2: ");
		System.out.println(sd.getSpell(2));
		System.out.println("Spell #3: ");
		System.out.println(sd.getSpell(3));
		System.out.println();
		
		// Test addSpell(spell)
		Spell newSpell = new Spell("test", "garbage", 1000);
		System.out.println("Adding new spell: ");
		newSpell.setId(sd.addSpell(newSpell));
		System.out.println(sd.getSpell(newSpell.getId()));
		System.out.println();
		
		// Test updateSpell(spell)
		newSpell.setStock(99);
		newSpell.setType(SpellType.SORCERY);
		sd.updateSpell(newSpell);
		System.out.println("Updating stock and type of test spell: ");
		System.out.println(sd.getSpell(newSpell.getId()));
		System.out.println();
		
		// Test deleteSpell(id)
		sd.deleteSpell(newSpell.getId());
		System.out.println("Deleting test spell (should not appear in list): ");
		System.out.println(sd.getSpells());
	}
}
