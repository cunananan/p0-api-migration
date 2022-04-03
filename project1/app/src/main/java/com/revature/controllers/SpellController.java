package com.revature.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.Spell.SpellType;
import com.revature.models.SpellDto;
import com.revature.services.SpellService;

@RestController
@RequestMapping("/spells")
public class SpellController {
	
	private SpellService ss;
	
	public SpellController(SpellService ss) {
		super();
		this.ss = ss;
	}
	
	@GetMapping
	public ResponseEntity<List<SpellDto>> getSpells(@RequestParam(name="search", required=false) String searchStr,
	                                                @RequestParam(name="type", required=false) String typeStr,
	                                                @RequestParam(name="priceCap", required=false) String priceCapStr,
	                                                @RequestParam(name="inStock", required=false) String inStockStr,
	                                                @RequestParam(name="intCap", required=false) String intCapStr,
	                                                @RequestParam(name="faiCap", required=false) String faiCapStr,
	                                                @RequestParam(name="arcCap", required=false) String arcCapStr)
	{
		SpellType type = null;
		int priceCap = -1;
		Boolean inStock = null;
		int intCap = -1;
		int faiCap = -1;
		int arcCap = -1;
		boolean queriesAllNull = true;
		
		if (searchStr != null && !StringUtils.isBlank(searchStr)) {
			queriesAllNull = false;
		}
		if (typeStr != null) {
			type = spellTypeFromString(typeStr);
			if (type != SpellType.NOT_SET) queriesAllNull = false;
		}
		if (priceCapStr != null) {
			priceCap = intFromString(priceCapStr);
			if (priceCap >= 0) queriesAllNull = false;
		}
		if (inStockStr != null) {
			inStock = booleanFromString(inStockStr);
			if (inStock != null) queriesAllNull = false;
		}
		if (intCapStr != null) {
			intCap = intFromString(intCapStr);
			if (intCap >= 0) queriesAllNull = false;
		}
		if (faiCapStr != null) {
			faiCap = intFromString(faiCapStr);
			if (faiCap >= 0) queriesAllNull = false;
		}
		if (arcCapStr != null) {
			arcCap = intFromString(arcCapStr);
			if (arcCap >= 0) queriesAllNull = false;
		}
		
		try {
			List<SpellDto> spells = (queriesAllNull)
			                            ? ss.getSpells()
			                            : ss.getSpellsByQuery(searchStr, type, priceCap, inStock, intCap, faiCap, arcCap);
			return new ResponseEntity<>(spells, HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			// TODO Logging
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SpellDto> getSpellById(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(ss.getSpellById(id), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			// TODO Logging
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<String> addSpell(@RequestBody SpellDto newSpell) {
		SpellDto spell = ss.addSpell(newSpell);
		return new ResponseEntity<>("New spell \"" + spell.name + "\" was added at index " + spell.id,
				                    HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SpellDto> updateSpell(@PathVariable("id") int id, @RequestBody SpellDto updates) {
		updates.id = id;
		try {
			return new ResponseEntity<>(ss.updateSpell(updates), HttpStatus.CREATED);
		} catch (ItemNotFoundException e) {
			// TODO Logging
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSpell(@PathVariable("id") int id) {
		try {
			SpellDto spell = ss.deleteSpell(id);
			return new ResponseEntity<>("Deleted spell \"" + spell.name + "\"", HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			// TODO Logging
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private int intFromString(String intStr) {
		try {
			return Integer.parseInt(intStr);
		} catch (NumberFormatException e) {
			// TODO Logging
			return -1;
		}
	}
	
	private Boolean booleanFromString(String boolStr) {
		if (boolStr.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		} else if (boolStr.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		} else {
			// TODO Logging
			return null;
		}
	}
	
	private SpellType spellTypeFromString(String typeStr) {
		try {
			return SpellType.valueOf(typeStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			// TODO Logging
			return SpellType.NOT_SET;
		}
	}
}




