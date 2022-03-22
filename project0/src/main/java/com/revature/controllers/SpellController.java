package com.revature.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.PartialSpell;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.services.SpellService;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class SpellController {
	
	private static SpellService ss = new SpellService();
	private static Logger log = LogManager.getRootLogger();
	
	public static void getSpells(Context ctx) {
		List<Spell> spells;
		SpellType type = SpellType.NOT_SET;
		int priceCap = -1;
		Boolean inStock = null;
		int intCap = -1;
		int faiCap = -1;
		int arcCap = -1;
		boolean queriesAllNull = true;
		
		String typeStr = ctx.queryParam("type");
		if (typeStr != null) {
			if (typeStr.equalsIgnoreCase(SpellType.INCANTATION.name())) {
				type = SpellType.INCANTATION;
				queriesAllNull = false;
			} else if (typeStr.equalsIgnoreCase(SpellType.SORCERY.name())) {
				type = SpellType.SORCERY;
				queriesAllNull = false;
			}
		}
		String priceCapStr = ctx.queryParam("priceCap");
		if (isInteger(priceCapStr)) {
			priceCap = Integer.parseInt(priceCapStr);
			queriesAllNull = false;
		}
		String inStockStr = ctx.queryParam("inStock");
		if (inStockStr != null && (inStockStr.equalsIgnoreCase("true")
		                           || inStockStr.equalsIgnoreCase("false")))
		{
			inStock = Boolean.valueOf(inStockStr);
			queriesAllNull = false;
		}
		String intCapStr = ctx.queryParam("intCap");
		if (isInteger(intCapStr)) {
			intCap = Integer.parseInt(intCapStr);
			queriesAllNull = false;
		}
		String faiCapStr = ctx.queryParam("faiCap");
		if (isInteger(faiCapStr)) {
			faiCap = Integer.parseInt(faiCapStr);
			queriesAllNull = false;
		}
		String arcCapStr = ctx.queryParam("arcCap");
		if (isInteger(arcCapStr)) {
			arcCap = Integer.parseInt(arcCapStr);
			queriesAllNull = false;
		}
		try {
			if (queriesAllNull) {
				spells = ss.getSpells();
			} else {
				spells = ss.getSpells(type, priceCap, inStock, intCap, faiCap, arcCap);
			}
			ctx.json(spells);
			ctx.status(HttpCode.OK);
		} catch (ItemNotFoundException e) {
			ctx.result(e.getMessage());
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
	
	public static void getSpellById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			ctx.json(ss.getSpell(id));
			ctx.status(HttpCode.OK);
		} catch (ItemNotFoundException e) {
			ctx.result(e.getMessage());
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
	
	public static void addSpell(Context ctx) {
		Spell spell = ctx.bodyAsClass(Spell.class);
		try {
			int id = ss.addSpell(spell);
			spell.setId(id);
			ctx.result("Added new spell \"" + spell.getName() + "\" at index " + id);
			ctx.status(HttpCode.CREATED);
			log.info("Added following spell to database:{}",
			         System.lineSeparator() + "\t" + spell.toStringFull());
		} catch (InsertionFailureException e) {
			ctx.result(e.getMessage());
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			log.error("Failed attempt to add following spell to database:{}",
			          System.lineSeparator() + "\t" + spell.toStringFull());
		}
	}
	
	public static void updateSpell(Context ctx) {
		PartialSpell spell = ctx.bodyAsClass(PartialSpell.class);
		spell.id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Spell updatedSpell = ss.updateSpell(spell);
			ctx.result("Updated spell #" + spell.id);
			ctx.status(HttpCode.OK);
			log.info("Updated spell in database to the following:{}",
			         System.lineSeparator() + "\t" + updatedSpell.toStringFull());
		} catch (ItemNotFoundException e) {
			ctx.result(e.getMessage());
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
	
	public static void deleteSpell(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Spell deletedSpell = ss.deleteSpell(id);
			ctx.result("Removed spell #" + id);
			ctx.status(HttpCode.OK);
			log.info("Removed following spell from database:{}",
			         System.lineSeparator() + "\t" + deletedSpell.toStringFull());
		} catch (ItemNotFoundException e) {
			ctx.result(e.getMessage());
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
	
	// Some sketchy biznasty going on over here
	public static boolean isInteger(String intString) {
		if (intString != null) {
			try {
				Integer.parseInt(intString);
				return true;
			} catch (NumberFormatException e) {
				System.out.println("NumberFormatException caught: " + e.getMessage());
			}
		}
		return false;
	}
}




