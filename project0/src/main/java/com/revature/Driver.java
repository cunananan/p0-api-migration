package com.revature;

import java.util.List;

import javax.sound.sampled.AudioFileFormat.Type;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.PartialSpell;
import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.services.SpellService;

import io.javalin.Javalin;
import io.javalin.http.HttpCode;

public class Driver {
	
	public static void main(String[] args) {
		SpellService ss = new SpellService();
		
		Javalin app = Javalin.create().start();
		
		app.get("spells", (ctx) -> {
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
			if (priceCapStr != null) {
				priceCap = Integer.parseInt(priceCapStr);
				queriesAllNull = false;
			}
			String inStockStr = ctx.queryParam("inStock");
			if (inStockStr != null) {
				if (inStockStr.equalsIgnoreCase("true") || inStockStr.equalsIgnoreCase("false")) {
					inStock = Boolean.valueOf(inStockStr);
					queriesAllNull = false;
				}
			}
			String intCapStr = ctx.queryParam("intCap");
			if (intCapStr != null) {
				intCap = Integer.parseInt(intCapStr);
				queriesAllNull = false;
			}
			String faiCapStr = ctx.queryParam("faiCap");
			if (faiCapStr != null) {
				faiCap = Integer.parseInt(faiCapStr);
				queriesAllNull = false;
			}
			String arcCapStr = ctx.queryParam("arcCap");
			if (arcCapStr != null) {
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
		});
		
		app.get("spells/{id}", (ctx) -> {
			int id = Integer.parseInt(ctx.pathParam("id"));
			try {
				ctx.json(ss.getSpell(id));
				ctx.status(HttpCode.OK);
			} catch (ItemNotFoundException e) {
				ctx.result(e.getMessage());
				ctx.status(HttpCode.NOT_FOUND);
			}
		});
		
		app.post("spells/*", (ctx) -> {
			Spell spell = ctx.bodyAsClass(Spell.class);
			try {
				int id = ss.addSpell(spell);
				ctx.result("Added new spell \"" + spell.getName() + "\" index " + id);
				ctx.status(HttpCode.CREATED);
			} catch (InsertionFailureException e) {
				ctx.result(e.getMessage());
				ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			}
		});
		
		app.put("spells/{id}", (ctx) -> {
			PartialSpell spell = ctx.bodyAsClass(PartialSpell.class);
			spell.id = Integer.parseInt(ctx.pathParam("id"));
			try {
				ss.updateSpell(spell);
				ctx.result("Updated spell #" + spell.id);
				ctx.status(HttpCode.OK);
			} catch (ItemNotFoundException e) {
				ctx.result(e.getMessage());
				ctx.status(HttpCode.NOT_FOUND);
			}
		});
		
		app.delete("spells/{id}", (ctx) -> {
			int id = Integer.parseInt(ctx.pathParam("id"));
			try {
				ss.deleteSpell(id);
				ctx.result("Removed spell #" + id);
				ctx.status(HttpCode.OK);
			} catch (ItemNotFoundException e) {
				ctx.result(e.getMessage());
				ctx.status(HttpCode.NOT_FOUND);
			}
		});
	}
}
