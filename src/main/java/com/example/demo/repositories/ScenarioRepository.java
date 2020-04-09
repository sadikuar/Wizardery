package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

}
