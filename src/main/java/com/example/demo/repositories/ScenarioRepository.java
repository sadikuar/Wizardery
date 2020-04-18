package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

	Optional<Scenario> findByName(String name);

	List<Scenario> findByNameLike(String name);
}
