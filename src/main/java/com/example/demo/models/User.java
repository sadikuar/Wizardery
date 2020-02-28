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
import javax.persistence.Table;

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
    @JoinTable(name = "favorites",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "rpg_id", referencedColumnName = "id",nullable = false, updatable = false)})
    private Set<Rpg> favoriteRpgs = new HashSet<>();

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

	public boolean isPublic() {
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
}
