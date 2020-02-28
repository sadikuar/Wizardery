package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class File {
	@Id
	@GeneratedValue
	private long id;

	public long getId() {
		return id;
	}
	
	@Column
	private String name;
	
	@Column
	private String fileLocation;
	
	@ManyToOne
	@JoinColumn
	private Rpg rpg;
	
	@ManyToOne
	@JoinColumn
	private Scenario scenario;

	public void setId(long id) {
		this.id = id;
	}
	
	public File() {
		// nothing
	}
}
