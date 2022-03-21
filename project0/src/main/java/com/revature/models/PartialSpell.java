package com.revature.models;

import com.revature.models.Spell.SpellType;

public class PartialSpell extends PartialItem {
	
	public SpellType type;
	public FpCost fpCost;
	public int slotsUsed;
	public StatRequirement statRequirement;
	
	
	public class FpCost {
		public int cast;
		public int charge;
	}
	
	public class StatRequirement {
		public int intelligence;
		public int faith;
		public int arcane;
	}
	
	public PartialSpell() {
		super();
		
		type = SpellType.NOT_SET;
		
		fpCost = new FpCost();
		fpCost.cast = -1;
		fpCost.charge = -1;
		
		slotsUsed = -1;
		
		statRequirement = new StatRequirement();
		statRequirement.intelligence = -1;
		statRequirement.faith = -1;
		statRequirement.arcane = -1;
	}
	
	public void copyValidFieldsToItem(Spell target) {
		super.copyValidFieldsToItem(target);
		
		target.setType(type);
		
		int castCost = (fpCost.cast >= 0) ? fpCost.cast : target.getFpCost().cast;
		int chargeCost = (fpCost.charge >= 0) ? fpCost.charge : target.getFpCost().charge;
		target.setFpCost(castCost, chargeCost);
		
		if (slotsUsed >= 1) target.setSlotsUsed(slotsUsed);
		
		int intReq = (statRequirement.intelligence > 0) ? statRequirement.intelligence
		                 : target.getStatRequirement().intelligence;
		int faiReq = (statRequirement.faith > 0) ? statRequirement.faith
                         : target.getStatRequirement().faith;
		int arcReq = (statRequirement.arcane > 0) ? statRequirement.arcane
                         : target.getStatRequirement().arcane;
		target.setStatRequirement(intReq, faiReq, arcReq);
	}
	
	
}
