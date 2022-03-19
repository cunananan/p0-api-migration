package com.revature.controllers;

import io.javalin.Javalin;

public class Driver {
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start();
		
		app.get("spells", (ctx) -> {
			
		});
	}
}
