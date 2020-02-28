package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class File {
	@Id
	@GeneratedValue
	private long id;
	
	
}
