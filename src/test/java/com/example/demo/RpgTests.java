package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.RoleEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RpgTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private RoleRepository roleRepository;

	private static User user = null;
	private static Rpg rpg = null;

	private Rpg createValidRpg() {
		Rpg rpg = new Rpg();
		rpg.setCreator(user);
		rpg.setDescription("Rpg description");
		rpg.setName("Testing");
		rpg.setRules("Some rules");
		return rpg;
	}

	private User createUser(String name) {
		User user = new User();
		user.setUsername(name);
		user.setEmail("user.test@wizardery.ch");
		user.setDescription("Testing description");
		user.setImageUrl("");
		user.setPublic(false);
		user.setPassword("password");
		return user;
	}
	
	private User createUser() {
		return createUser("WizarderyTestUser");
	}

	private boolean checkIfSameRpg(Rpg rpg1, Rpg rpg2) {
		boolean isSame = rpg1.getId() == rpg2.getId();
		isSame &= rpg1.getName().contentEquals(rpg2.getName());
		isSame &= rpg1.getCreator().getId() == rpg2.getCreator().getId();
		isSame &= rpg1.getDescription().contentEquals(rpg2.getDescription());
		isSame &= rpg1.getRules().contentEquals(rpg2.getRules());
		return isSame;
	}

	@BeforeEach
	public void checkRepositories() {
		assertNotNull(fileRepository);
		assertNotNull(roleRepository);
		assertNotNull(rpgRepository);
		assertNotNull(userRepository);
		user = createUser();
		user.setRole(roleRepository.findByName("" + RoleEnum.USER));
		userRepository.save(user);
	}

	@AfterEach
	public void resetDatabse() {
		// Delete the rpg used for tests to keep database clean
		if (user != null) {
			userRepository.delete(user);
			user = null;
		}
		if (rpg != null) {
			rpgRepository.delete(rpg);
			rpg = null;
		}

	}

	@Test
	@Order(1)
	@DisplayName("Add valid rpg in database")
	public void createRpg() {
		rpg = createValidRpg();
		rpgRepository.save(rpg);

		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());
	}

	@Test
	@Order(2)
	@DisplayName("Retrieve RPG from database")
	public void retrieveRpg() {
		// Add RPG to database
		rpg = createValidRpg();
		rpgRepository.save(rpg);
		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());

		// Retrieve RPG
		Optional<Rpg> optionalId = rpgRepository.findById(rpg.getId());
		Optional<Rpg> optionalName = rpgRepository.findByName(rpg.getName());
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(optionalName.isPresent(), "findById didn't work");
		assertTrue(checkIfSameRpg(optionalId.get(), rpg));
		assertTrue(checkIfSameRpg(optionalName.get(), rpg));
	}

	@Test
	@Order(3)
	@DisplayName("Update user in database")
	public void updateRpg() {
		// Add RPG to database
		rpg = createValidRpg();
		rpgRepository.save(rpg);
		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());

		// Update RPG
		String newDescription = "Some new description";
		String newName = "new name";
		String newRules = "The new rules of this RPG";
		User newCreator = createUser("NewUser");
		userRepository.save(newCreator);
		rpg.setCreator(newCreator);
		rpg.setDescription(newDescription);
		rpg.setName(newName);
		rpg.setRules(newRules);
		rpg.setFiles(rpg.getFiles());
		rpgRepository.save(rpg);
		
		// Retrieve RPG
		Optional<Rpg> optionalId = rpgRepository.findById(rpg.getId());
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(checkIfSameRpg(optionalId.get(), rpg));
		
		userRepository.delete(newCreator);
	}

	@Test
	@Order(4)
	@DisplayName("Delete user from database")
	public void deleteRpg() {
		rpg = createValidRpg();
		rpgRepository.save(rpg);

		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());
		
		rpgRepository.deleteById(rpg.getId());
		assertFalse(rpgRepository.findById(rpg.getId()).isPresent());
		rpg=null;
	}

}
