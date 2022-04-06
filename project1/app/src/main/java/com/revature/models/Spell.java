package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="spells")
public class Spell extends Item {
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SpellType type;
	private int castFpCost;
	private int chargeFpCost;
	private int slotsUsed;
	private int intRequirement;
	private int faiRequirement;
	private int arcRequirement;
	
	// Nested classes
	public enum SpellType {
		NOT_SET,
		SORCERY,
		INCANTATION;
	}
	
	// Constructors
	public Spell() {
		super();
		setCategory(ItemCategory.SPELL);
		type = SpellType.NOT_SET;
		castFpCost = 0;
		chargeFpCost = 0;
		slotsUsed = 1;
		intRequirement = 0;
		faiRequirement = 0;
		arcRequirement = 0;
	}
	public Spell(int id, String name, String description, int price, int stock, SpellType type, int castFpCost,
			int chargeFpCost, int slotsUsed, int intRequirement, int faiRequirement, int arcRequirement) {
		super(ItemCategory.SPELL, id, name, description, price, stock);
		this.type = type;
		this.castFpCost = castFpCost;
		this.chargeFpCost = chargeFpCost;
		this.slotsUsed = slotsUsed;
		this.intRequirement = intRequirement;
		this.faiRequirement = faiRequirement;
		this.arcRequirement = arcRequirement;
	}
	
	// Setters and Getters
	public SpellType getType() {
		return type;
	}
	public void setType(SpellType type) {
		if (type != null && type != SpellType.NOT_SET) this.type = type;
	}
	public int getCastFpCost() {
		return castFpCost;
	}
	public void setCastFpCost(int castFpCost) {
		this.castFpCost = Math.max(0, castFpCost);
	}
	public int getChargeFpCost() {
		return chargeFpCost;
	}
	public void setChargeFpCost(int chargeFpCost) {
		this.chargeFpCost = Math.max(0, chargeFpCost);
	}
	public int getSlotsUsed() {
		return slotsUsed;
	}
	public void setSlotsUsed(int slotsUsed) {
		this.slotsUsed = Math.max(1, slotsUsed);
	}
	public int getIntRequirement() {
		return intRequirement;
	}
	public void setIntRequirement(int intRequirement) {
		this.intRequirement = Math.max(0, intRequirement);
	}
	public int getFaiRequirement() {
		return faiRequirement;
	}
	public void setFaiRequirement(int faiRequirement) {
		this.faiRequirement = Math.max(0, faiRequirement);
	}
	public int getArcRequirement() {
		return arcRequirement;
	}
	public void setArcRequirement(int arcRequirement) {
		this.arcRequirement = Math.max(0, arcRequirement);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(arcRequirement, castFpCost, chargeFpCost, faiRequirement, intRequirement,
				slotsUsed, type);
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
		return arcRequirement == other.arcRequirement && castFpCost == other.castFpCost
				&& chargeFpCost == other.chargeFpCost && faiRequirement == other.faiRequirement
				&& intRequirement == other.intRequirement && slotsUsed == other.slotsUsed && type == other.type;
	}
	
	@Override
	public String toStringPartial() {
		return super.toString();
	}
	@Override
	public String toString() {
		return "Spell [" + super.toStringPartial() + ", type=" + type + ", castFpCost=" + castFpCost + ", chargeFpCost="
				+ chargeFpCost + ", slotsUsed=" + slotsUsed + ", intRequirement=" + intRequirement + ", faiRequirement="
				+ faiRequirement + ", arcRequirement=" + arcRequirement + "]";
	}
}
