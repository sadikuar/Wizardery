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
@Table(name = "scenarios")
public class Scenario {
	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn
	private User creator;

	@Column(length = 10000)
	@Length(min = 0,max = 10000)
	private String description;

	@Column(length = 10000)
	@Length(min = 0,max = 10000)
	private String quests;

	@Column
	private String difficulty;

	@Column
	private int minPlayers;

	@Column
	private int maxPlayers;

	@Column
	private int advisedPlayers;

	@Column
	private int timeApproximation;
	
	@Column
	private String patchNote;

	@ManyToMany(mappedBy = "favoriteScenarios", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@ManyToOne
	@JoinColumn
	private Rpg rpg;

	@OneToMany(mappedBy = "scenario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<File> files;

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

	public int getAdvisedPlayers() {
		return advisedPlayers;
	}

	public void setAdvisedPlayers(int advicedPlayers) {
		this.advisedPlayers = advicedPlayers;
	}

	public int getTimeApproximation() {
		return timeApproximation;
	}

	public void setTimeApproximation(int timeApproximation) {
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public MultipartFile[] getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(MultipartFile[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public String getPatchNote() {
		return patchNote;
	}

	public void setPatchNote(String patchNote) {
		this.patchNote = patchNote;
	}

}
