package com.revature;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import com.revature.controllers.SpellController;

import io.javalin.Javalin;

public class Driver {
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create((config) -> {
			// Boilerplate from javalin-demo
			config.enableCorsForAllOrigins();
			config.defaultContentType = "application/json";	
		});
		app.start();
		
		app.routes(() -> {
			// handles requests to the /spells endpoint
			path("spells", () -> {
				get(SpellController::getSpells);
				post(SpellController::addSpell);
				// handles requests to the /spells/{id} endpoint
				path("{id}", () -> {
					get(SpellController::getSpellById);
					put(SpellController::updateSpell);
					delete(SpellController::deleteSpell);
				});
			});
		});
	}
}
