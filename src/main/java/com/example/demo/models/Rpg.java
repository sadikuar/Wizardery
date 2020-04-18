package com.example.demo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "rpgs")
public class Rpg {
	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

	@Column(length = 10000)
	@Length(min = 0,max = 10000)
	private String description;

	@Column(length = 10000)
	@Length(min = 0,max = 10000)
	private String rules;

	@ManyToOne
	@JoinColumn
	private User creator;

	@ManyToMany(mappedBy = "favoriteRpgs", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "rpg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Scenario> scenarios;

	@OneToMany(mappedBy = "rpg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<File> files;

	transient MultipartFile[] uploadedFiles;

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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(Set<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public MultipartFile[] getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(MultipartFile[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Rpg() {
		// nothing
	}
}
