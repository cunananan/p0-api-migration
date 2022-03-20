package com.revature;

import com.revature.exceptions.InsertionFailureException;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.models.Spell;
import com.revature.services.SpellService;

import io.javalin.Javalin;
import io.javalin.http.HttpCode;

public class Driver {
	
	public static void main(String[] args) {
		SpellService ss = new SpellService();
		
		Javalin app = Javalin.create().start();
		
		app.get("spells", (ctx) -> {
			try {
				ctx.json(ss.getSpells());
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
			Spell spell = ctx.bodyAsClass(Spell.class);
			spell.setId(Integer.parseInt(ctx.pathParam("id")));
			try {
				ss.updateSpell(spell);
				ctx.result("Updated spell #" + spell.getId());
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
