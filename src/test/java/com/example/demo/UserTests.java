package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.RoleEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableJpaRepositories
public class UserTests { 

	@LocalServerPort
	private int port;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private static User user = null;
	
	private User createValidTestUser() {
		User user = new User();
		user.setUsername("UserTestWizardery");
		user.setEmail("user.test@wizardery.ch");
		user.setDescription("Testing description");
		user.setImageUrl("");
		user.setPublic(false);
		user.setRole(roleRepository.findByName(""+RoleEnum.USER));
		user.setPassword("password");
		return user;
	}
	
	@BeforeEach
	public void checkRepositoryNotNull() {
		assertThat(userRepository).isNotNull();
		user = null;
	}
	
	@AfterEach
	public void resetDatabse() {
		// Delete the user used for tests to keep database clean
		if(user != null) {
			userRepository.deleteById(user.getId());
		}
		assertFalse(userRepository.findById(user.getId()).isPresent(), "User not correctly deleted from database");
	}
	
	
	
	@Test
	@DisplayName("Add valid user in database")
	public void createValidUserTest() {
		user = createValidTestUser();
		userRepository.save(user);
		assertNotNull(userRepository.findById(user.getId()).get());
	}
	
	@Test
	@DisplayName("Retrieve user from database")
	public void retriveUserTest() {
		// Add user in database
		user = createValidTestUser();
		userRepository.save(user);
		// Retrieve
		
		Optional<User> retrivedId = userRepository.findById(user.getId());
		User retrivedEmail = userRepository.findByEmail(user.getEmail());
		
		assertTrue(retrivedId.isPresent(), "findById didn't work");
		assertNotNull(retrivedEmail,"findByName didn't work");
		assertTrue(checkIfSameUser(retrivedId.get(), user));
		assertTrue(checkIfSameUser(retrivedEmail, user));
	}
	
	@Test
	@DisplayName("Update user in database")
	public void updateUserTest() {
		user = createValidTestUser();
		userRepository.save(user);
		
		String newDescription = "Some description";
		String newUsername = "UpdatedUsername";
		String newPassword = "UpdatedPassword";
		String newImageUrl = "url";
		Role newRole = roleRepository.findByName(""+RoleEnum.ADMIN);
		boolean newPulic = true;
		user.setUsername(newUsername);
		user.setDescription(newDescription);
		user.setPassword(newPassword);
		user.setImageUrl(newImageUrl);
		user.setRole(newRole);
		user.setPublic(newPulic);
		userRepository.save(user);
		
		Optional<User> optionalUpdated = userRepository.findById(user.getId());
		assertTrue(optionalUpdated.isPresent(),"findById didn't work after update");
		User updated = optionalUpdated.get();
		assertTrue(checkIfSameUser(user, updated), "User not properly updated");
		
	}
	
	private boolean checkIfSameUser(User user1, User user2) {
		boolean isSame = true;
		isSame &= user1.getId() == user2.getId();
		isSame &= user1.getIsPublic() == user2.getIsPublic();
		isSame &= user1.getDescription().contentEquals(user2.getDescription());
		isSame &= user1.getEmail().contentEquals(user2.getEmail());
		isSame &= user1.getImageUrl().contentEquals(user2.getImageUrl());
		isSame &= user1.getPassword().contentEquals(user2.getPassword());
		isSame &= user1.getUsername().contentEquals(user2.getUsername());
		return isSame;
	}
	
	
	
	
}
