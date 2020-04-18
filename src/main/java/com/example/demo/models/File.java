package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Rpg getRpg() {
		return rpg;
	}

	public void setRpg(Rpg rpg) {
		this.rpg = rpg;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public File() {
		// nothing
	}
}
