package com.example.demo.seeders;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.models.File;
import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.utils.Directory;

@Component
public class FileSeeder implements TableSeeder {

	private static final String[] tabRpgFileNames = { "DnD_BasicRules.pdf", "DnD_CharacterSheet.pdf" };
	private static final String[] tabRpgNames = { "Dungeons & Dragons", "Dungeons & Dragons" };
	private static final String[] tabScenarioFileNames = {"Curse_of_Strahd.pdf"};
	private static final String[] tabScenarioNames = {"Curse of Strahd"};

	private FileRepository fileRepository;
	private RpgRepository rpgRepository;
	private ScenarioRepository scenarioRepository;

	public FileSeeder(FileRepository fileRepository, RpgRepository rpgRepository,
			ScenarioRepository scenarioRepository) {
		this.fileRepository = fileRepository;
		this.rpgRepository = rpgRepository;
		this.scenarioRepository = scenarioRepository;
	}

	@Override
	public void seed() {
		if(fileRepository.findAll().isEmpty()) {
			seedRpgs();
			seedScenarios();
		}
	}

	private void seedRpgs() {
		int maxIndice = Math.min(tabRpgFileNames.length, tabRpgNames.length);
		for (int i = 0; i < maxIndice; i++) {
			Optional<Rpg> optionalRpg = rpgRepository.findByName(tabRpgNames[i]);
			if (optionalRpg.isPresent()) {
				String fileName = tabRpgFileNames[i];
				if(fileExists(fileName)) {
					File file = createFile(fileName, optionalRpg.get());
					fileRepository.save(file);
				}
			}
		}
	}

	private void seedScenarios() {
		int maxIndice = Math.min(tabScenarioFileNames.length, tabScenarioNames.length);
		for (int i = 0; i < maxIndice; i++) {
			Optional<Scenario> optionalScenario = scenarioRepository.findByName(tabScenarioNames[i]);
			if (optionalScenario.isPresent()) {
				String fileName = tabScenarioFileNames[i];
				if(fileExists(fileName)) {
					File file = createFile(fileName, optionalScenario.get());
					fileRepository.save(file);
				}
			}
		}
	}

	private File createFile(String fileName, Rpg rpg) {
		File file = createFile(fileName);
		file.setRpg(rpg);
		return file;
	}

	private File createFile(String fileName, Scenario scenario) {
		File file = createFile(fileName);
		file.setScenario(scenario);
		return file;
	}

	private File createFile(String fileName) {
		File file = new File();
		file.setName(fileName);
		file.setFileLocation(Directory.SEEDER_DIR + fileName);
		return file;
	}
	
	private boolean fileExists(String fileName) {
		java.io.File file = new java.io.File(Directory.SEEDER_DIR+fileName);
		return file.exists();
	}

}
