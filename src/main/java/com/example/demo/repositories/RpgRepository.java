package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Rpg;

public interface RpgRepository extends JpaRepository<Rpg, Long> {
	Optional<Rpg> findByName(String name);
	
	Page<Rpg> findAll(Pageable pageable);
}
