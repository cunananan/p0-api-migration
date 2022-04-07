package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.ItemNotFoundException;
import com.revature.exceptions.ValidationException;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.models.SpellDto;
import com.revature.repositories.SpellRepository;

@ExtendWith(MockitoExtension.class)
public class SpellServiceTests {

	private static SpellRepository mockRepo;
	private static SpellService ss;
	private static List<Spell> spells;
	private static List<SpellDto> spellsDto;

	@BeforeAll
	public static void setup() {
		mockRepo = mock(SpellRepository.class);
		ss = new SpellService(mockRepo);

		Spell spell1 = new Spell(1, "first", "", 0, 0, SpellType.SORCERY, 1000, 1, 1, 10, 0, 0);
		Spell spell2 = new Spell(2, "second", "", 0, 0, SpellType.INCANTATION, 0, 0, 1, 0, 10, 0);
		spells = new ArrayList<>();
		spells.add(spell1);
		spells.add(spell2);

		spellsDto = new ArrayList<>();
		spellsDto.add(new SpellDto(spell1));
		spellsDto.add(new SpellDto(spell2));
	}

	@Test
	void getSpellsTestX() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<Spell>());
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpells();
		});
	}

	@Test
	void getSpellsTest0() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(spells);
		assertDoesNotThrow(() -> {
			assertEquals(spellsDto, ss.getSpells());
		});
	}

	@Test
	void getSpellsByQueryTestX() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(spells);
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpellsByQuery("    \t", null, 0, true, 0, 0, 0);
		});
	}

	@Test
	void getSpellsByQueryTest0() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(spells);
		assertDoesNotThrow(() -> {
			assertEquals(spellsDto, ss.getSpellsByQuery(null, SpellType.NOT_SET, -1, null, -2, -3, -4));
		});
	}

	@Test
	void getSpellsByQueryTest1() {
		when(mockRepo.findByNameContainingIgnoreCaseOrderByIdAsc("sec")).thenReturn(spells.subList(1, 2));
		when(mockRepo.findByDescriptionContainingIgnoreCaseOrderByIdAsc("sec")).thenReturn(new ArrayList<>());
		assertDoesNotThrow(() -> {
			assertEquals(spellsDto.subList(1, 2),
					ss.getSpellsByQuery("sec", SpellType.INCANTATION, 500, false, 10, 10, 10));
		});
	}

	@Test
	void getSpellByIdTestX() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> {
			ss.getSpellById(0);
		});
	}

	@Test
	void getSpellByIdTest0() {
		when(mockRepo.findById(1)).thenReturn(Optional.of(spells.get(0)));
		assertDoesNotThrow(() -> {
			assertEquals(spellsDto.get(0), ss.getSpellById(1));
		});
	}

	@Test
	void addSpellTest0() {
		when(mockRepo.save(new Spell())).thenReturn(new Spell());
		assertEquals(new SpellDto(new Spell()), ss.addSpell(null));
	}

	@Test
	void addSpellTest1() {
		Spell s = new Spell();
		SpellDto sd0 = new SpellDto(s);
		SpellDto sd1 = new SpellDto(s);
		
		when(mockRepo.save(s)).thenReturn(s);
		assertEquals(sd1, ss.addSpell(sd0));
	}

	@Test
	void updateSpellTestX0() {
		assertThrows(ValidationException.class, () -> {
			ss.updateSpell(null);
		});
	}

	@Test
	void updateSpellTestX1() {
		when(mockRepo.findById(-1)).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> {
			ss.updateSpell(new SpellDto());
		});
	}

	@Test
	void updateSpellTest0() {
		SpellDto su = new SpellDto();
		su.id = 1;
		Spell s = new Spell();
		s.setId(1);
		
		when(mockRepo.findById(1)).thenReturn(Optional.of(s));
		when(mockRepo.save(s)).thenReturn(s);
		assertDoesNotThrow(() -> {
			assertEquals(new SpellDto(s), ss.updateSpell(su));
		});
	}

	@Test
	void deleteSpellTestX() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(ItemNotFoundException.class, () -> {
			ss.deleteSpell(0);
		});
	}

	@Test
	void deleteSpellTest0() {
		when(mockRepo.findById(1)).thenReturn(Optional.of(spells.get(0)));
		assertDoesNotThrow(() -> {
			assertEquals(spellsDto.get(0), ss.deleteSpell(1));
		});
	}
}
