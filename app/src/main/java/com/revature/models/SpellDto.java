package com.revature.models;

import java.util.Objects;

import com.revature.models.Item.ItemCategory;
import com.revature.models.Spell.SpellType;

public class SpellDto extends ItemDto {
	
	public SpellType type;
	public FpCost fpCost;
	public int slotsUsed;
	public StatRequirement statRequirement;
	
	public class FpCost {
		public int cast;
		public int charge;
		
		public FpCost() {
			cast = -1;
			charge = -1;
		}
		@Override
		public int hashCode() {
			return Objects.hash(cast, charge);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FpCost other = (FpCost) obj;
			return cast == other.cast && charge == other.charge;
		}
		@Override
		public String toString() {
			return "fpCost [cast=" + cast + ", charge=" + charge + "]";
		}
	}
	
	public class StatRequirement {
		public int intelligence;
		public int faith;
		public int arcane;
		
		public StatRequirement() {
			intelligence = -1;
			faith = -1;
			arcane = -1;
		}
		@Override
		public int hashCode() {
			return Objects.hash(arcane, faith, intelligence);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StatRequirement other = (StatRequirement) obj;
			return arcane == other.arcane && faith == other.faith && intelligence == other.intelligence;
		}
		@Override
		public String toString() {
			return "statRequirement [intelligence=" + intelligence + ", faith=" + faith + ", arcane=" + arcane + "]";
		}
	}
	
	public SpellDto() {
		super();
		type = null;
		fpCost = new FpCost();
		slotsUsed = -1;
		statRequirement = new StatRequirement();
	}
	
	public SpellDto(Spell source) {
		super();
		fpCost = new FpCost();
		statRequirement = new StatRequirement();
		copyFrom(source);
	}
	
	public void copyFrom(Spell source) {
		super.copyFrom(source);
		category = ItemCategory.SPELL;
		type = source.getType();
		
		fpCost.cast = source.getCastFpCost();
		fpCost.charge = source.getChargeFpCost();
		
		slotsUsed = source.getSlotsUsed();
		
		statRequirement.intelligence = source.getIntRequirement();
		statRequirement.faith = source.getFaiRequirement();
		statRequirement.arcane = source.getArcRequirement();
	}
	
	public void copyTo(Spell target) {
		super.copyTo(target);
		target.setCategory(ItemCategory.SPELL);
		if (type != null) target.setType(type);
		
		if (fpCost.cast >= 0) target.setCastFpCost(fpCost.cast);
		if (fpCost.charge >= 0) target.setChargeFpCost(fpCost.charge);
		
		if (slotsUsed >= 1) target.setSlotsUsed(slotsUsed);
		
		if (statRequirement.intelligence >= 0) target.setIntRequirement(statRequirement.intelligence);
		if (statRequirement.faith >= 0) target.setFaiRequirement(statRequirement.faith);
		if (statRequirement.arcane >= 0) target.setArcRequirement(statRequirement.arcane);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fpCost, slotsUsed, statRequirement, type);
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
		SpellDto other = (SpellDto) obj;
		return Objects.equals(fpCost, other.fpCost) && slotsUsed == other.slotsUsed
				&& Objects.equals(statRequirement, other.statRequirement) && type == other.type;
	}

	@Override
	public String toStringPartial() {
		return super.toString();
	}
	@Override
	public String toString() {
		return "Spell [" + super.toStringPartial() + ", type=" + type + ", " + fpCost.toString()
				+ ", slotsUsed=" + slotsUsed + ", " + statRequirement.toString() + "]";
	}
}
