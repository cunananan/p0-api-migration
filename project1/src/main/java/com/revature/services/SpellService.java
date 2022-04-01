package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.Spell;
import com.revature.models.SpellDto;
import com.revature.models.Spell.SpellType;
import com.revature.repositories.SpellRepository;

@Service
public class SpellService {

	private SpellRepository sr;
	
	@Autowired
	public SpellService(SpellRepository sr) {
		this.sr = sr;
	}
	
	public List<SpellDto> getSpells() throws ItemNotFoundException {
		List<Spell> spells = sr.findAllByOrderByIdAsc();
		if (spells.isEmpty()) {
			throw new ItemNotFoundException();
		}
		// Convert List<Spell> to List<SpellDto>
		return spells.stream().map(SpellDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public List<SpellDto> getSpellsByQuery(String search, SpellType type, int priceCap, Boolean inStock,
            int intCap, int faiCap, int arcCap) throws ItemNotFoundException
	{	
		List<Spell> spells = (search == null || search.isBlank())
		                         ? sr.findAllByOrderByIdAsc()
	                             : findBySearchNameAndDescription(search);
		Stream<Spell> ss = spells.stream();
		if (type != null && type != SpellType.NOT_SET)
			ss = ss.filter(s -> s.getType() == type);
		if (priceCap >= 0)
			ss = ss.filter(s -> s.getPrice() <= priceCap);
		if (inStock != null)
			// if inStock checked, allow spells with stock > 0; else only allow spells with stock <= 0
			ss = ss.filter(s -> Boolean.TRUE.equals(inStock) ^ s.getStock() <= 0);
		if (intCap >= 0)
			ss = ss.filter(s -> s.getIntRequirement() <= intCap);
		if (faiCap >= 0)
			ss = ss.filter(s -> s.getFaiRequirement() <= faiCap);
		if (arcCap >= 0)
			ss = ss.filter(s -> s.getArcRequirement() <= arcCap);
		
		List<SpellDto> spellsDto = ss.map(SpellDto::new).collect(Collectors.toList());
		if (spellsDto.isEmpty()) {
			throw new ItemNotFoundException();
		}
		// Convert List<Spell> to List<SpellDto>
		return spellsDto;
	}
	
	public SpellDto getSpellById(int id) throws ItemNotFoundException {
		Spell spell = sr.findById(id).orElseThrow(ItemNotFoundException::new);
		return new SpellDto(spell);
	}
	
	public SpellDto addSpell(SpellDto newSpell) {
		if (newSpell == null) newSpell = new SpellDto();
		newSpell.id = -1;
		Spell spell = new Spell();
		newSpell.copyTo(spell);
		newSpell.copyFrom(sr.save(spell));
		return newSpell;
	}
	
	@Transactional
	public SpellDto updateSpell(SpellDto spellUpdates) throws ItemNotFoundException {
		if (spellUpdates == null) {
			throw new ItemNotFoundException();
		} else {
			Spell spell = sr.findById(spellUpdates.id).orElseThrow(ItemNotFoundException::new);
			spellUpdates.copyTo(spell);
			spellUpdates.copyFrom(sr.save(spell));
		}
		return spellUpdates;
	}
	
	@Transactional
	public SpellDto deleteSpell(int id) throws ItemNotFoundException {
		Spell removedSpell = sr.findById(id).orElseThrow(ItemNotFoundException::new);
		sr.deleteById(id);
		return new SpellDto(removedSpell);
	}
	
	// This is slow and I kind of hate it
	private List<Spell> findBySearchNameAndDescription(String search) {
		List<Spell> nameList = sr.findByNameContainingIgnoreCaseOrderByIdAsc(search);
		List<Spell> descList = sr.findByDescriptionContainingIgnoreCaseOrderByIdAsc(search);
		// The returned items should be in the following order:
		// - items that match by their name, by id asc
		// - items that match by their description, by id asc
		for (int i = 0; i < descList.size(); i++) {
			if (!nameList.contains(descList.get(i))) {
				nameList.add(descList.get(i));
			}
		}
		return nameList;
	}
}





