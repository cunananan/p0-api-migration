package com.revature.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Spell;
import com.revature.models.Spell.SpellType;
import com.revature.util.ConnectionUtil;

public class SpellPostgres implements SpellDao {
	
	private static Logger log = LogManager.getRootLogger();
	
	@Override
	public List<Spell> getSpells() {
		List<Spell> spells = new ArrayList<>();
		String sql = "SELECT * FROM spells ORDER BY id ASC;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				spells.add(createSpellFromRecord(rs));
			}
		} catch (SQLException | IOException e) {
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
		}
		return spells;
	}
	
	@Override
	public List<Spell> getSpells(SpellType type, int priceCap, Boolean inStock, int intCap, int faiCap, int arcCap)
	{
		List<Spell> spells = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM spells");
		
		boolean whereWasAdded = false;
		if (type != SpellType.NOT_SET) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(" type_id = ");
			sql.append(type.ordinal());
		}
		if (priceCap >= 0) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(" price <= ");
			sql.append(priceCap);
		}
		if (inStock != null) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(Boolean.TRUE.equals(inStock) ? " stock > 0" : " stock = 0");
		}
		if (intCap >= 0) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(" int_requirement <= ");
			sql.append(intCap);
		}
		if (faiCap >= 0) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(" fai_requirement <= ");
			sql.append(faiCap);
		}
		if (arcCap >= 0) {
			if (!whereWasAdded) {
				sql.append(" WHERE");
				whereWasAdded = true;
			} else {
				sql.append(" AND");
			}
			sql.append(" arc_requirement <= ");
			sql.append(arcCap);
		}
		sql.append(" ORDER BY id ASC;");
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				spells.add(createSpellFromRecord(rs));
			}
		} catch (SQLException | IOException e) {
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
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
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
		}
		return spell;
	}

	@Override
	public int insertSpell(Spell spell) {
		int id = -1;
		String sql = "INSERT INTO spells (id, name, description, price, stock, type_id, cast_fp_cost, "
                     + "charge_fp_cost, slots_used, int_requirement, fai_requirement, arc_requirement) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
		             //         i  n  d  p  s  ti if hf su ir fr ar
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, spell.getId());
			prepareStatementWithSpellFields(ps, spell, 2);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException | IOException e) {
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
		}
		return id;
	}

	@Override
	public int appendSpell(Spell spell) {
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
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
		}
		return genId;
	}

	@Override
	public Spell deleteSpell(int id) {
		Spell spell = null;
		String sql = "DELETE FROM spells WHERE id = ? RETURNING *;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				spell = createSpellFromRecord(rs);
			}
		} catch (SQLException | IOException e) {
			logExceptionStackTrace(e, 5);
//			e.printStackTrace();
		}
		return spell;
	}

	@Override
	public Spell updateSpell(Spell spell) {
		String sql = "UPDATE spells SET name = ?, description = ?, price = ?, stock = ?, type_id = ?, "
		             + "cast_fp_cost = ?, charge_fp_cost = ?, slots_used = ?, int_requirement = ?, "
		             + "fai_requirement = ?, arc_requirement = ? WHERE id = ? RETURNING *;";
		
		try (Connection c = ConnectionUtil.getConnection()) {
			PreparedStatement ps = c.prepareStatement(sql);
			prepareStatementWithSpellFields(ps, spell, 1);
			ps.setInt(12, spell.getId());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// No need to construct from record; should be identical to spell passed in
				return spell;
			}
		} catch (SQLException | IOException e) {
			logExceptionStackTrace(e);
//			e.printStackTrace();
		}
		return null;
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
	
	private void logExceptionStackTrace(Exception e) {
		log.error("Exception was thrown while accessing database: ", e.fillInStackTrace());
	}
	
	private void logExceptionStackTrace(Exception e, int numOfElements) {
		StackTraceElement[] elts = e.getStackTrace();
		StringBuilder msg = new StringBuilder("Exception was thrown while accessing database: ");
		for (int i = 0; i < numOfElements && i < elts.length; i++) {
			msg.append(System.lineSeparator());
			msg.append("\t");
			msg.append(elts[i]);
		}
		log.error(msg);
	}
}
