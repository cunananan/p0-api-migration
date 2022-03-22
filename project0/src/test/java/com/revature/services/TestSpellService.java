package com.revature.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.PartialSpell;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.persistence.SpellDao;

@ExtendWith(MockitoExtension.class)
class TestSpellService {
	
	private static SpellDao mockDao;
	private static SpellService ss;
	private static List<Spell> spells;
	private static PartialSpell partial;
	
	@BeforeAll
	public static void setup() {
		mockDao = mock(SpellDao.class);
		ss = new SpellService(mockDao);
		
		spells = new ArrayList<>();
		spells.add(new Spell(1, "first", "", 0, 0));
		spells.add(new Spell(2, "second", "", 0, 0));
		spells.add(new Spell(3, "third", "", 0, 0));
		
		partial = new PartialSpell();
		partial.id = 2;
		partial.type = SpellType.SORCERY;
	}
	
	@Test
	void getSpellsTest0() {
		when(mockDao.getSpells()).thenReturn(spells);
		assertDoesNotThrow(() -> {
			assertEquals(spells, ss.getSpells());
		});
		verify(mockDao).getSpells();
	}
	@Test
	void getSpellsTest1() {
		when(mockDao.getSpells()).thenReturn(null);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpells();
		});
		verify(mockDao, times(2)).getSpells();
	}
	@Test
	void getSpellsTest2() {
		when(mockDao.getSpells()).thenReturn(new ArrayList<>());
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpells();
		});
		verify(mockDao, times(3)).getSpells();
	}
	
	@Test
	void getSpellsWithQueryTest1() {
		when(mockDao.getSpells(SpellType.SORCERY, -1, null, 15, 0, 0)).thenReturn(spells);
		assertDoesNotThrow(() -> {
			assertEquals(spells, ss.getSpells(SpellType.SORCERY, -1, null, 15, 0, 0));
		});
		verify(mockDao).getSpells(SpellType.SORCERY, -1, null, 15, 0, 0);
	}
	
	@Test
	void getSpellsWithQueryTest2() {
		when(mockDao.getSpells(SpellType.NOT_SET, 1000, true, 0, 0, 0)).thenReturn(null);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpells(SpellType.NOT_SET, 1000, true, 0, 0, 0);
		});
		verify(mockDao).getSpells(SpellType.NOT_SET, 1000, true, 0, 0, 0);
	}
	
	@Test
	void getSpellTest0() {
		when(mockDao.getSpell(1)).thenReturn(spells.get(0));
		assertDoesNotThrow(() -> {
			assertEquals(spells.get(0), ss.getSpell(1));
		});
		verify(mockDao).getSpell(1);
	}
	@Test
	void getSpellTest1() {
		when(mockDao.getSpell(0)).thenReturn(null);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpell(0);
		});
		verify(mockDao).getSpell(0);
	}
	
	@Test
	void addSpellTest0() {
		
		when(mockDao.appendSpell(spells.get(0))).thenReturn(1);
		assertDoesNotThrow(() -> {
			assertEquals(1, ss.addSpell(spells.get(0)));
		});
		verify(mockDao).appendSpell(spells.get(0));
	}
	@Test
	void addSpellTest1() {
		when(mockDao.appendSpell(null)).thenReturn(-1);
		assertThrows(InsertionFailureException.class, () -> {
			ss.addSpell(null);
		});
		verify(mockDao).appendSpell(null);
	}
	
	@Test
	void deleteSpellTest0() {
		when(mockDao.deleteSpell(1)).thenReturn(spells.get(0));
		assertDoesNotThrow(() -> { ss.deleteSpell(1); });
		verify(mockDao).deleteSpell(1);
	}
	@Test
	void deleteSpellTest1() {
		when(mockDao.deleteSpell(0)).thenReturn(null);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.deleteSpell(0);
		});
		verify(mockDao).deleteSpell(0);
	}
	
	@Test
	void updateSpellTest0() {
		when(mockDao.getSpell(2)).thenReturn(spells.get(1));
		when(mockDao.updateSpell(spells.get(1))).thenReturn(spells.get(1));
		assertDoesNotThrow(() -> { ss.updateSpell(partial); });
		verify(mockDao).getSpell(2);
		verify(mockDao).updateSpell(spells.get(1));
	}
	@Test
	void updateSpellTest1() {
		when(mockDao.getSpell(3)).thenReturn(null);
		when(mockDao.appendSpell(null)).thenReturn(-1);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.updateSpell(null);
		});
		verify(mockDao, never()).getSpell(3);
		verify(mockDao, never()).updateSpell(null);
	}
}




