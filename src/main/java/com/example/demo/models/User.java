package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Column
	private String email;

	@Column
	private String password;

	@Transient
	private String passwordConfirm;

	@Column
	private String username;

	@Column
	private String description;

	@Column
	private String imageUrl;

	@Column
	private boolean isPublic;

	@ManyToOne
	@JoinColumn
	private Role role;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "favorite_rpgs", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "rpg_id", referencedColumnName = "id", nullable = true, updatable = false, columnDefinition = "bigint(1) default 0") })
	private Set<Rpg> favoriteRpgs = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "favorite_scenarios", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "scenario_id", referencedColumnName = "id", nullable = true, updatable = false, columnDefinition = "bigint(1) default 0") })
	private Set<Scenario> favoriteScenarios = new HashSet<>();

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rpg> rpgs;

	transient MultipartFile uploadedFile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Rpg> getFavoriteRpgs() {
		return favoriteRpgs;
	}

	public void setFavoriteRpgs(Set<Rpg> favoriteRpgs) {
		this.favoriteRpgs = favoriteRpgs;
	}

	public void addFavoriteRpg(Rpg rpg) {
		Set<Rpg> setRpgs = getFavoriteRpgs();
		setRpgs.add(rpg);
		setFavoriteRpgs(setRpgs);
	}

	public void removeFavoriteRPG(Rpg rpg) {
		Set<Rpg> setRpgs = getFavoriteRpgs();
		setRpgs.remove(rpg);
		setFavoriteRpgs(setRpgs);
	}

	public Set<Rpg> getRpgs() {
		return rpgs;
	}

	public void setRpgs(Set<Rpg> rpgs) {
		this.rpgs = rpgs;
	}
	
	public Set<Scenario> getFavoriteScenarios() {
		return favoriteScenarios;
	}

	public void setFavoriteScenarios(Set<Scenario> favoriteScenarios) {
		System.out.println("SET FAV");
		this.favoriteScenarios = favoriteScenarios;
		System.out.println("OKKKK");
	}

	public void addFavoriteScenario(Scenario scenario) {
		Set<Scenario> setScenarios = getFavoriteScenarios();
		setScenarios.add(scenario);
		setFavoriteScenarios(setScenarios);
	}

	public void removeFavoriteScenario(Scenario scenario) {
		Set<Scenario> setScenarios = getFavoriteScenarios();
		setScenarios.remove(scenario);
		setFavoriteScenarios(setScenarios);
	}

	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(MultipartFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", passwordConfirm=" + passwordConfirm
				+ ", username=" + username + ", description=" + description + ", imageUrl=" + imageUrl + ", isPublic="
				+ isPublic + ", role=" + role + "]";
	}

	public User() {
		// nothing
	}
}
