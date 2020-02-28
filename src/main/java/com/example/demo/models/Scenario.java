package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "scenarios")
public class Scenario {
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String quests;
	
	@Column
	private String difficulty;
	
	@Column
	private int minPlayers;
	
	@Column
	private int maxPlayers;
	
	@Column
	private int advicedPlayers;
	
	@Column
	private double timeApproximation;
	
	@ManyToOne
	@JoinColumn
	private Rpg rpg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuests() {
		return quests;
	}

	public void setQuests(String quests) {
		this.quests = quests;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getAdvicedPlayers() {
		return advicedPlayers;
	}

	public void setAdvicedPlayers(int advicedPlayers) {
		this.advicedPlayers = advicedPlayers;
	}

	public double getTimeApproximation() {
		return timeApproximation;
	}

	public void setTimeApproximation(double timeApproximation) {
		this.timeApproximation = timeApproximation;
	}
	
	public Rpg getRpg() {
		return rpg;
	}

	public void setRpg(Rpg rpg) {
		this.rpg = rpg;
	}

	public Scenario() {
		// nothing
	}
}
