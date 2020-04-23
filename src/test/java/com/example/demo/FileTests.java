package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileTests {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private ScenarioRepository scenarioRepository;

	private static File file = null;

	private File createValidFile() {
		File file = new File();
		file.setFileLocation("/");
		file.setName("Some_file.txt");
		return file;
	}

	private boolean checkIfSameFile(File file1, File file2) {
		boolean isSame = file1.getId() == file2.getId();
		isSame &= file1.getFileLocation().contentEquals(file2.getFileLocation());
		isSame &= file1.getName().contentEquals(file2.getName());
		return isSame;
	}

	@BeforeEach
	public void checkRepositories() {
		assertNotNull(fileRepository);
	}

	@AfterEach
	public void resetDatabse() {
		// Delete the file used for tests to keep database clean
		if (file != null) {
			fileRepository.deleteById(file.getId());
			file = null;
		}
	}

	@Test
	@Order(1)
	@DisplayName("Add valid file in database")
	public void createFileTest() {
		file = createValidFile();
		fileRepository.save(file);
		assertTrue(fileRepository.findById(file.getId()).isPresent());
	}

	@Test
	@Order(2)
	@DisplayName("Add valid file with rpg in database")
	public void createFileWithRpgTest() {
		String seededRpgName = "Blades in the dark";
		Optional<Rpg> optionalRpg = rpgRepository.findByName(seededRpgName);
		optionalRpg.ifPresent(rpg -> {
			file = createValidFile();
			file.setRpg(rpg);
			fileRepository.save(file);
			Optional<File> optionalFile = fileRepository.findById(file.getId());
			assertTrue(optionalFile.isPresent());
			assertTrue(optionalFile.get().getRpg().getId() == rpg.getId());
		});
	}

	@Test
	@Order(3)
	@DisplayName("Add valid file with scenario in database")
	public void createFileWithScenarioTest() {
		String seededScenarioName = "Curse of Strahd";
		Optional<Scenario> optionalScenario = scenarioRepository.findByName(seededScenarioName);
		optionalScenario.ifPresent(scenario -> {
			file = createValidFile();
			file.setScenario(scenario);
			fileRepository.save(file);
			Optional<File> optionalFile = fileRepository.findById(file.getId());
			assertTrue(optionalFile.isPresent());
			assertTrue(optionalFile.get().getScenario().getId() == scenario.getId());
		});
	}

	@Test
	@Order(4)
	@DisplayName("Retrieve file from database")
	public void retrieveRpgTest() {
		// Add File to database
		file = createValidFile();
		fileRepository.save(file);
		assertTrue(fileRepository.findById(file.getId()).isPresent());

		// Retrieve RPG
		Optional<File> optionalId = fileRepository.findById(file.getId());
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(checkIfSameFile(optionalId.get(), file));
	}

	@Test
	@Order(5)
	@DisplayName("Update file in database")
	public void updateRpgTest() {
		// Add File to database
		file = createValidFile();
		fileRepository.save(file);
		assertTrue(fileRepository.findById(file.getId()).isPresent());

		// Update File
		String newLocation = "/newLoc";
		String newName = "new.txt";
		file.setFileLocation(newLocation);
		file.setName(newName);
		fileRepository.save(file);

		// Retrieve File
		Optional<File> optionalId = fileRepository.findById(file.getId());
		assertTrue(optionalId.isPresent(), "findById didn't work");
		assertTrue(checkIfSameFile(optionalId.get(), file));
	}

	@Test
	@Order(6)
	@DisplayName("Delete file from database")
	public void deleteRpgTest() {
		file = createValidFile();
		fileRepository.save(file);

		assertTrue(fileRepository.findById(file.getId()).isPresent());

		fileRepository.deleteById(file.getId());
		assertFalse(fileRepository.findById(file.getId()).isPresent());
		file = null;
	}

}
