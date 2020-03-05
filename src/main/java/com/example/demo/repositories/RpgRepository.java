package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Rpg;

public interface RpgRepository extends JpaRepository<Rpg, Long> {

}
