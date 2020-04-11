package com.example.demo.seeders;

import org.springframework.stereotype.Component;

import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;

@Component
public class RpgSeeder implements TableSeeder {

	private static final String[] tabNames = { "Dungeons & Dragons" };
	private static final String[] tabDescriptions = {
			"The many worlds of Dungeons & Dragons are places of magic and monsters, of brave warriors and spectacular adventures. They begin with a foundation of medieval fantasy and then add the creatures, places, and magic that make these worlds unique." };

	private static final String[] tabRules = {
			"## Classes\nThe available classes are:\n* Fighter\n* Wizard\n* Cleric\n* Rogue\n* Ranger\n## Races\nThe races are:\n* Human\n* Elf\n* Dwarf\n* Halfling\n* Gnome\n* Half-orc" };

	private RpgRepository rpgRepository;
	private UserRepository userRepository;

	public RpgSeeder(RpgRepository rpgRepository, UserRepository userRepository) {
		this.rpgRepository = rpgRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void seed() {
		if (rpgRepository.findAll().isEmpty()) {
			User creator = userRepository.findByEmail("admin@wizardery.ch");
			int maxIndice = Math.min(tabNames.length, Math.min(tabDescriptions.length, tabRules.length));
			for (int i = 0; i < maxIndice; i++) {
				Rpg rpg = createRpg(tabNames[i], tabDescriptions[i], tabRules[i], creator);
				rpgRepository.save(rpg);
			}
		}
	}

	private Rpg createRpg(String name, String description, String rules, User creator) {
		Rpg rpg = new Rpg();
		rpg.setName(name);
		rpg.setDescription(description);
		rpg.setRules(rules);
		rpg.setCreator(creator);
		return rpg;
	}

}
