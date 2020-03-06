package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
