package com.revature.models;

import java.util.Objects;

public class Spell extends Item {
	
	private SpellType type;
	private FpCost fpCost;
	private int slotsUsed;
	private StatRequirement statRequirement;
	
	// Nested classes
	public enum SpellType {
		NOT_SET,
		SORCERY,
		INCANTATION;
	}
	public class FpCost {
		public int cast;
		public int charge;
		
		public FpCost() {
			super();
			cast = 0;
			charge = 0;
		}
		public FpCost(FpCost other) {
			super();
			this.cast = other.cast;
			this.charge = other.charge;
		}
		@Override
		public String toString() {
			return "[cast=" + cast + ", charge=" + charge + "]";
		}
	}
	public class StatRequirement {
		public int intelligence;
		public int faith;
		public int arcane;
		
		public StatRequirement() {
			super();
			intelligence = 0;
			faith = 0;
			arcane = 0;
		}
		public StatRequirement(StatRequirement other) {
			super();
			this.intelligence = other.intelligence;
			this.faith = other.faith;
			this.arcane = other.arcane;
		}
		@Override
		public String toString() {
			return "[intelligence=" + intelligence + ", faith=" + faith + ", arcane=" + arcane + "]";
		}
	}
	
	// Constructors
	public Spell() {
		super();
		type = SpellType.NOT_SET;
		fpCost = new FpCost();
		slotsUsed = 1;
		statRequirement = new StatRequirement();
	}
	// To initialize the other fields, just use the setters
	public Spell(String name, String description, int price) {
		super(name, description, price);
		type = SpellType.NOT_SET;
		fpCost = new FpCost();
		slotsUsed = 1;
		statRequirement = new StatRequirement();
	}
	public Spell(int id, String name, String description, int price, int stock) {
		super(id, name, description, price, stock);
		type = SpellType.NOT_SET;
		fpCost = new FpCost();
		slotsUsed = 1;
		statRequirement = new StatRequirement();
	}
	public Spell(Spell other) {
		super(other);
		type = other.type;
		fpCost = new FpCost(other.fpCost);
		slotsUsed = other.slotsUsed;
		statRequirement = new StatRequirement(other.statRequirement);
	}

	// Setters and Getters
	public SpellType getType() {
		return type;
	}
	public void setType(SpellType type) {
		if (type != SpellType.NOT_SET) {
			this.type = type;
		}
	}
	public FpCost getFpCost() {
		return new FpCost(fpCost);
	}
	public void setFpCost(int castCost, int chargeCost) {
		this.fpCost.cast = Math.max(0, castCost);
		this.fpCost.charge = Math.max(0, chargeCost);
	}
	public int getSlotsUsed() {
		return slotsUsed;
	}
	public void setSlotsUsed(int slotsUsed) {
		this.slotsUsed = Math.max(1, slotsUsed);
	}
	public StatRequirement getStatRequirement() {
		return new StatRequirement(statRequirement);
	}
	public void setStatRequirement(int intRequirement, int faiRequirement, int arcRequirement) {
		this.statRequirement.intelligence = Math.max(0, intRequirement);
		this.statRequirement.faith = Math.max(0, faiRequirement);
		this.statRequirement.arcane = Math.max(0, arcRequirement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fpCost.cast, fpCost.charge, slotsUsed, type,
				statRequirement.intelligence, statRequirement.faith, statRequirement.arcane);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spell other = (Spell) obj;
		return fpCost.cast == other.fpCost.cast && fpCost.charge == other.fpCost.charge
				&& slotsUsed == other.slotsUsed && type == other.type
				&& statRequirement.intelligence == other.statRequirement.intelligence
				&& statRequirement.faith == other.statRequirement.faith
				&& statRequirement.arcane == other.statRequirement.arcane;
	}
	
	public String toStringFull() {
		return "Spell [" + toStringPartial() + ", type=" + type + ", fpCost=" + fpCost.toString()
				+ ", slotsUsed=" + slotsUsed + ", statRequirement=" + statRequirement.toString() + "]";
	}
	
	
}
