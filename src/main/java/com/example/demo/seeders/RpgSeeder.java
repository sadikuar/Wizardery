package com.example.demo.seeders;

import org.springframework.stereotype.Component;

import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;

@Component
public class RpgSeeder implements TableSeeder {

	private static final String[] tabNames = { "Dungeons & Dragons", "Call of Cthulhu" };
	private static final String[] tabDescriptions = {
			"The many worlds of Dungeons & Dragons are places of magic and monsters, of brave warriors and spectacular adventures. They begin with a foundation of medieval fantasy and then add the creatures, places, and magic that make these worlds unique.",
			"The setting of Call of Cthulhu is a darker version of our world, based on H.P. Lovecraft's observation that, \"The oldest and strongest emotion of mankind is fear, and the strongest kind of fear is fear of the unknown.\".\n\nThe players take the"+
			" roles of ordinary people, drawn into the realm of the mysterious: detectives, criminals, scholars, artists, war veterans, etc. Often, happenings begin innocently enough, until more and more of the workings behind the scenes are revealed. As the"+
			" characters learn more of the true horrors of the world and the irrelevance of humanity, their sanity inevitably withers away. To access the tools they need to defeat the horrors - mystic knowledge and magic - the characters must be willing to give up some of their sanity."};

	private static final String[] tabRules = {
			"## Classes\nThe available classes are:\n* Fighter\n* Wizard\n* Cleric\n* Rogue\n* Ranger\n## Races\nThe races are:\n* Human\n* Elf\n* Dwarf\n* Halfling\n* Gnome\n* Half-orc",
			"You can buy the rule books [here](https://www.chaosium.com/7th-edition-rules/)."};

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
