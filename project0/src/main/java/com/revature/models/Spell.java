package com.revature.models;

import java.util.Objects;

public class Spell extends Item {
	
	private SpellType type;
	private FpCost fpCost;
	private int slotsUsed;
	private StatRequirement statRequirement;
	
	public enum SpellType {
		NOT_SET,
		SORCERY,
		INCANTATION
	}
	
	public class FpCost {
		public int activate;
		public int hold;
		
		public FpCost() {
			super();
		}
		public FpCost(FpCost other) {
			super();
			this.activate = other.activate;
			this.hold = other.hold;
		}
	}
	
	public class StatRequirement {
		public int intelligence;
		public int faith;
		public int arcane;
		
		public StatRequirement() {
			super();
		}
		public StatRequirement(StatRequirement other) {
			super();
			this.intelligence = other.intelligence;
			this.faith = other.faith;
			this.arcane = other.arcane;
		}
	}
	
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
	
	public Spell(Spell other) {
		super((Item)other);
		type = other.type;
		fpCost = new FpCost(other.fpCost);
		slotsUsed = other.slotsUsed;
		statRequirement = new StatRequirement(other.statRequirement);
	}

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

	public void setFpCost(int activateCost, int holdCost) {
		this.fpCost.activate = Math.max(0, activateCost);
		this.fpCost.hold = Math.max(0, holdCost);
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
		result = prime * result + Objects.hash(fpCost.activate, fpCost.hold, slotsUsed, type,
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
		return fpCost.activate == other.fpCost.activate && fpCost.hold == other.fpCost.hold
				&& slotsUsed == other.slotsUsed && type == other.type
				&& statRequirement.intelligence == other.statRequirement.intelligence
				&& statRequirement.faith == other.statRequirement.faith
				&& statRequirement.arcane == other.statRequirement.arcane;
	}
}
