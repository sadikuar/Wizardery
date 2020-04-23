package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.RoleEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScenarioTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ScenarioRepository scenarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private static User user = null;
	private static Rpg rpg = null;
	private static Scenario scenario = null;
	
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
	
	private Scenario createValidScenario() {
		Scenario scenario = new Scenario();
		scenario.setAdvisedPlayers(2);
		scenario.setCreator(user);
		scenario.setDescription("Description");
		scenario.setDifficulty("Easy");
		scenario.setMaxPlayers(3);
		scenario.setMinPlayers(1);
		scenario.setName("WizarderyTest Scenario");
		scenario.setPatchNote("Some patch note");
		scenario.setQuests("Some quests");
		scenario.setRpg(rpg);
		scenario.setTimeApproximation(120);
		return scenario;
	}

	private boolean checkIfSameScenario(Scenario scenario1, Scenario scenario2) {
		boolean isSame = scenario1.getId() == scenario2.getId();
		isSame &= scenario1.getName().contentEquals(scenario2.getName());
		isSame &= scenario1.getCreator().getId() == scenario2.getCreator().getId();
		isSame &= scenario1.getDescription().contentEquals(scenario2.getDescription());
		isSame &= scenario1.getAdvisedPlayers() == scenario2.getAdvisedPlayers();
		isSame &= scenario1.getDifficulty().contentEquals(scenario2.getDifficulty());
		isSame &= scenario1.getMaxPlayers() == scenario2.getMaxPlayers();
		isSame &= scenario1.getMinPlayers() == scenario2.getMinPlayers();
		isSame &= scenario1.getPatchNote().contentEquals(scenario2.getPatchNote());
		isSame &= scenario1.getQuests().contentEquals(scenario2.getQuests());
		isSame &= scenario1.getTimeApproximation() == scenario2.getTimeApproximation();
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
		rpg = createValidRpg();
		rpg.setCreator(user);
		rpgRepository.save(rpg);
	}

	@AfterEach
	public void resetDatabse() {
		// Delete the scenario,rpg and user used for tests to keep database clean
		if(scenario != null) {
			scenarioRepository.deleteById(scenario.getId());
			scenario = null;
		}
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
	@DisplayName("Add valid scenario in database")
	public void addScenarioTest() {
		// Add scenario to database
		scenario = createValidScenario();
		scenarioRepository.save(scenario);
		
		assertTrue(scenarioRepository.findById(scenario.getId()).isPresent());
	}
	
	@Test
	@Order(2)
	@DisplayName("Retrieve scenario from database")
	public void retrieveScenarioTest(){
		// Add scenario to database
		scenario = createValidScenario();
		scenarioRepository.save(scenario);
		assertTrue(scenarioRepository.findById(scenario.getId()).isPresent());
		
		//Retrive sceanrio
		Optional<Scenario> optionalId = scenarioRepository.findById(scenario.getId());
		Optional<Scenario> optionalName = scenarioRepository.findByName(scenario.getName());
		
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(optionalName.isPresent(), "findByName didn't work");
		assertTrue(checkIfSameScenario(optionalId.get(), scenario));
		assertTrue(checkIfSameScenario(optionalName.get(), scenario));
	}
	
	@Test
	@Order(3)
	@DisplayName("Update scenario in database")
	public void updateScenarioTest() {
		// Add scenario to database
		scenario = createValidScenario();
		scenarioRepository.save(scenario);
		assertTrue(scenarioRepository.findById(scenario.getId()).isPresent());
		
		// update scenario
		int newAdvised = 3;
		int newMax = 4;
		int newMin = 2;
		int newTimeApprox = 100;
		String newName = "Some new name";
		String newDescription = "Some new description";
		String newDifficulty = "Medium";
		String newPatchNote = "Updated scenario";
		String newQuests = "Some new quests";
		scenario.setAdvisedPlayers(newAdvised);
		scenario.setDescription(newDescription);
		scenario.setDifficulty(newDifficulty);
		scenario.setMaxPlayers(newMax);
		scenario.setMinPlayers(newMin);
		scenario.setName(newName);
		scenario.setPatchNote(newPatchNote);
		scenario.setQuests(newQuests);
		scenario.setTimeApproximation(newTimeApprox);
		HashSet<File> files = new HashSet<File>();
		scenario.setFiles(files);
		scenarioRepository.save(scenario);
		
		//Retrieve scenario
		Optional<Scenario> optional = scenarioRepository.findById(scenario.getId());
		assertTrue(optional.isPresent());
		assertTrue(checkIfSameScenario(optional.get(), scenario));
	}
	
	@Test
	@Order(4)
	@DisplayName("Delete scenario from database")
	public void deleteScenarioTest() {
		// Add scenario to database
		scenario = createValidScenario();
		scenarioRepository.save(scenario);
		assertTrue(scenarioRepository.findById(scenario.getId()).isPresent());
		
		//Delete scenario from database
		scenarioRepository.deleteById(scenario.getId());
		assertFalse(scenarioRepository.findById(scenario.getId()).isPresent());		
		scenario=null;
	}

}
