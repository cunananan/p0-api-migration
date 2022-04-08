package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.Spell;

@Repository
public interface SpellRepository extends JpaRepository<Spell, Integer> {
	
	List<Spell> findAllByOrderByIdAsc();
	
	List<Spell> findByNameContainingIgnoreCaseOrderByIdAsc(String search);
	
	List<Spell> findByDescriptionContainingIgnoreCaseOrderByIdAsc(String search);
}
