package com.example.demo.seeders;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;

@Component
public class ScenarioSeeder implements TableSeeder {

	private static final String[] tabNames = { "Curse of Strahd", "Baldur’s Gate: Descent into Avernus" };
	private static final String[] tabDescriptions = {
			"Under raging storm clouds, the vampire Count Strahd von Zarovich stands silhouetted against the ancient walls of Castle Ravenloft. Rumbling thunder pounds the castle spires. The wind's howling increases as he turns his gaze down toward the village of Barovia."
					+ " Far below, yet not beyond his keen eyesight, a party of adventurers has just entered his domain. Strahd's face forms the barest hint of a smile as his dark plan unfolds. He knew they were coming, and he knows why they came -- all according to his plan."
					+ " A lightning flash rips through the darkness, but Strahd is gone. Only the howling of the wind fills the midnight air. The master of Castle Ravenloft is having guests for dinner. And you are invited.",
			"Welcome to Baldur's Gate, a city of ambition and corruption situated at the crossroads of the Sword Coast. You’ve just started your adventuring career, but already find yourself embroiled in a plot that sprawls from the shadows of Baldur’s Gate to the front"
					+ " lines of the planes-spanning Blood War! Do you have what it takes to turn infernal war machines and nefarious contracts against the archdevil Zariel and her diabolical hordes? And can you ever hope to find your way home safely when pitted against the infinite evils of the Nine Hells?" };
	private static final String[] tabQuests = {
			"# Primary quests\n* Find Count Strahd von Zarovich and destroy him, so you can leave Ravenloft and return to Westgate.\n# Milestone quests\n* Find the Tome of Strahd in a place of bones (possibly inside Castle Ravenloft).\n* Find the Holy Symbol of Ravenkind in the west, at a pool blessed by sunlight."
					+ "\n* Find the Sunsword in a dead village, drowned by a river and ruled by one who has brought great evil into the world.\n* Find an ally in your quest, a fallen paladin from a fallen order of knights. RIctavio thinks they should be at Argynvostholt.\n# Minor quests\n* Find out why the Wizard"+
					" of Wines has stopped making deliveries to the Blue Water Inn.\n* Find Mad Mary Boguescu’s missing daughter, Gertruda.\n* Find a way to cure Donavich’s son, Doru – if there is one.\n* Learn why Izek Strazni is having Gadof Blinsky make dolls that resemble Ireena.",
			"You can buy the scenario's book [here](https://dnd.wizards.com/products/tabletop-games/rpg-products/baldursgate_descent)." };
	private static final String[] tabDifficulties = { "Medium", "Hard" };
	private static final String[] tabRpgName = { "Dungeons & Dragons", "Dungeons & Dragons" };
	private static final int[] tabMinPlayers = { 3 , 4 };
	private static final int[] tabMaxPlayers = { 6, 5 };
	private static final int[] tabAdvisedPlayers = { 4, 4 };
	private static final int[] tabTimeApprox = { 140, 240 };

	private ScenarioRepository scenarioRepository;
	private RpgRepository rpgRepository;
	private UserRepository userRepository;

	public ScenarioSeeder(ScenarioRepository scenarioRepository, RpgRepository rpgRepository,
			UserRepository userRepository) {
		this.scenarioRepository = scenarioRepository;
		this.rpgRepository = rpgRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void seed() {
		if (scenarioRepository.findAll().isEmpty()) {
			User creator = userRepository.findByEmail("admin@wizardery.ch");
			for (int i = 0; i < getMinTabLength(); i++) {
				Optional<Rpg> optionalRpg = rpgRepository.findByName(tabRpgName[i]);
				if (optionalRpg.isPresent()) {
					Rpg rpg = optionalRpg.get();
					Scenario scenario = createScenario(tabNames[i], tabDescriptions[i], tabQuests[i],
							tabDifficulties[i], tabMinPlayers[i], tabAdvisedPlayers[i], tabMaxPlayers[i],
							tabTimeApprox[i], rpg, creator);
					scenarioRepository.save(scenario);
				}
			}
		}

	}

	private int getMinTabLength() {
		int min = Math.min(tabNames.length, Math.min(tabDescriptions.length, tabQuests.length));
		min = Math.min(Math.min(tabDifficulties.length, tabRpgName.length), min);
		min = Math.min(Math.min(tabMaxPlayers.length, tabMinPlayers.length), min);
		min = Math.min(Math.min(tabAdvisedPlayers.length, tabTimeApprox.length), min);
		return min;
	}

	private Scenario createScenario(String name, String description, String quests, String difficulty, int minPlayers,
			int advisedPlayers, int maxPlayers, int timeApprox, Rpg rpg, User creator) {
		Scenario scenario = new Scenario();
		scenario.setName(name);
		scenario.setDescription(description);
		scenario.setQuests(quests);
		scenario.setDifficulty(difficulty);
		scenario.setMinPlayers(minPlayers);
		scenario.setMaxPlayers(maxPlayers);
		scenario.setAdvisedPlayers(advisedPlayers);
		scenario.setTimeApproximation(timeApprox);
		scenario.setRpg(rpg);
		scenario.setCreator(creator);
		scenario.setPatchNote("");
		return scenario;
	}

}
