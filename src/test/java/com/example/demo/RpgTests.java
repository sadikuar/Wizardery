package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.MarkdownParsingService;
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
		assertNotNull(roleRepository);
		assertNotNull(rpgRepository);
		assertNotNull(userRepository);
		user = createUser();
		user.setRole(roleRepository.findByName("" + RoleEnum.USER));
		userRepository.save(user);
	}

	@AfterEach
	public void resetDatabse() {
		// Delete the rpg and user used for tests to keep database clean
		if (rpg != null) {
			rpgRepository.deleteById(rpg.getId());
			rpg = null;
		}
		if (user != null) {
			userRepository.deleteById(user.getId());
			assertFalse(userRepository.findById(user.getId()).isPresent());
			user = null;
		}
	}

	@Test
	@Order(1)
	@DisplayName("Add valid rpg in database")
	public void createRpgTest() {
		rpg = createValidRpg();
		rpgRepository.save(rpg);
		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());
	}

	@Test
	@Order(2)
	@DisplayName("Retrieve rpg from database")
	public void retrieveRpgTest() {
		// Add RPG to database
		rpg = createValidRpg();
		rpgRepository.save(rpg);
		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());
		
		// Retrieve RPG
		Optional<Rpg> optionalId = rpgRepository.findById(rpg.getId());
		Optional<Rpg> optionalName = rpgRepository.findByName(rpg.getName());
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(optionalName.isPresent(), "findByName didn't work");
		assertTrue(checkIfSameRpg(optionalId.get(), rpg));
		assertTrue(checkIfSameRpg(optionalName.get(), rpg));
		
		rpgRepository.deleteById(rpg.getId());
		rpg=null;
	}

	@Test
	@Order(3)
	@DisplayName("Update rpg in database")
	public void updateRpgTest() {
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
		
		rpgRepository.deleteById(rpg.getId());
		userRepository.delete(newCreator);
		rpg=null;
	}

	@Test
	@Order(4)
	@DisplayName("Delete rpg from database")
	public void deleteRpgTest() {
		rpg = createValidRpg();
		rpgRepository.save(rpg);

		assertTrue(rpgRepository.findById(rpg.getId()).isPresent());
		
		rpgRepository.deleteById(rpg.getId());
		assertFalse(rpgRepository.findById(rpg.getId()).isPresent());
		rpg=null;
	}
	
	@Test
	@DisplayName("Add/remove rpg to user's favorite")
	public void addFavoriteRpgToUser() {
		Rpg rpg = createValidRpg();
		User user = createUser();
		
		user.addFavoriteRpg(rpg);
		
		assertTrue(user.getFavoriteRpgs().size()==1);
		
		user.removeFavoriteRPG(rpg);
		
		assertTrue(user.getFavoriteRpgs().isEmpty());
	}
	
	@Test
	@DisplayName("Rpg Markdown parsing")
	public void rpgMarkdownParsingTest() {
		Rpg rpg = createValidRpg();
		rpg.setDescription("# Heading1");
		rpg.setRules("* list item");
		Set<Scenario> setScenario = new HashSet<Scenario>();
		rpg.setScenarios(setScenario);
		
		MarkdownParsingService.parse(rpg);
		
		assertTrue(rpg.getDescription().contains("<h1>"));
		assertTrue(rpg.getRules().contains("<ul>"));
	}
}
