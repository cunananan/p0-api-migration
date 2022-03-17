package com.revature.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.util.ConnectionUtil;

public class SpellPostgres implements SpellDao {
	
	@Override
	public List<Spell> getSpells() {
		List<Spell> spells = new ArrayList<>();
		String sql = "SELECT * FROM spells;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				spells.add(createSpellFromRecord(rs));
			}
		} catch (SQLException | IOException e) {
			// TODO Proper Handling
			e.printStackTrace();
		}
		return spells;
	}

	@Override
	public Spell getSpell(int id) {
		Spell spell = null;
		String sql = "SELECT * FROM spells WHERE id = ?;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				spell = createSpellFromRecord(rs);
			}
		} catch (SQLException | IOException e) {
			// TODO Proper handling
			e.printStackTrace();
		}
		return spell;
	}

	@Override
	public int addSpell(Spell spell) {
		int genId = -1;
		String sql = "INSERT INTO spells (name, description, price, stock, type_id, cast_fp_cost, "
		             + "charge_fp_cost, slots_used, int_requirement, fai_requirement, arc_requirement) "
		             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
		             //         n  d  p  s  ti if hf su ir fr ar
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			prepareStatementWithSpellFields(ps, spell, 1);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				genId = rs.getInt("id");
			}
		} catch (SQLException | IOException e) {
			// TODO Proper handling
			e.printStackTrace();
		}
		return genId;
	}

	@Override
	public boolean deleteSpell(int id) {
		String sql = "DELETE FROM spells WHERE id = ? RETURNING *;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException | IOException e) {
			// TODO Proper handling
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateSpell(Spell spell) {
		String sql = "UPDATE spells SET name = ?, description = ?, price = ?, stock = ?, type_id = ?, "
		             + "cast_fp_cost = ?, charge_fp_cost = ?, slots_used = ?, int_requirement = ?, "
		             + "fai_requirement = ?, arc_requirement = ? WHERE id = ? RETURNING *;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			prepareStatementWithSpellFields(ps, spell, 1);
			ps.setInt(12, spell.getId());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException | IOException e) {
			// TODO Proper handling
			e.printStackTrace();
		}
		return false;
	}
	
	private Spell createSpellFromRecord(ResultSet rs) throws SQLException {
		Spell s = new Spell();
		s.setId(rs.getInt("id"));
		s.setName(rs.getString("name"));
		s.setDescription(rs.getString("description"));
		s.setPrice(rs.getInt("price"));
		s.setStock(rs.getInt("stock"));
		s.setType(SpellType.values()[rs.getInt("type_id")]);	// "cast" int to SpellType enum
		s.setFpCost(rs.getInt("cast_fp_cost"), rs.getInt("charge_fp_cost"));
		s.setSlotsUsed(rs.getInt("slots_used"));
		s.setStatRequirement(rs.getInt("int_requirement"),
		                     rs.getInt("fai_requirement"),
		                     rs.getInt("arc_requirement"));
		return s;
	}
	
	private void prepareStatementWithSpellFields(PreparedStatement ps, Spell spell, int startIdx)
			throws SQLException
	{
		ps.setString(startIdx, spell.getName());
		ps.setString(startIdx+1, spell.getDescription());
		ps.setInt(startIdx+2, spell.getPrice());
		ps.setInt(startIdx+3, spell.getStock());
		ps.setInt(startIdx+4, spell.getType().ordinal());
		ps.setInt(startIdx+5, spell.getFpCost().cast);
		ps.setInt(startIdx+6, spell.getFpCost().charge);
		ps.setInt(startIdx+7, spell.getSlotsUsed());
		ps.setInt(startIdx+8, spell.getStatRequirement().intelligence);
		ps.setInt(startIdx+9, spell.getStatRequirement().faith);
		ps.setInt(startIdx+10, spell.getStatRequirement().arcane);
	}
}
