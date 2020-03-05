package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Rpg;

public interface RpgRepository extends JpaRepository<Rpg, Long> {

}
