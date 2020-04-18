package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Rpg;

public interface RpgRepository extends JpaRepository<Rpg, Long> {
	
	Optional<Rpg> findByName(String name);
	
	List<Rpg> findByNameLike(String name);
}
