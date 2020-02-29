package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rpgs")
public class Rpg {
	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String rules;

//	@ManyToMany(mappedBy = "favoriteRpgs", fetch = FetchType.LAZY)
//	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "rpg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Scenario> scenarios;

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

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

//	public Set<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}

	public Set<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(Set<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public Rpg() {
		// nothing
	}
}
