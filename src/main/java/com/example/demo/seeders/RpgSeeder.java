package com.example.demo.seeders;

import org.springframework.stereotype.Component;

import com.example.demo.models.Rpg;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.UserRepository;

@Component
public class RpgSeeder implements TableSeeder {

	private static final String[] tabNames = { "Dungeons & Dragons", "Call of Cthulhu", "Blades in the dark",
			"Apocalypse World" };
	private static final String[] tabDescriptions = {
			"The many worlds of Dungeons & Dragons are places of magic and monsters, of brave warriors and spectacular adventures. They begin with a foundation of medieval fantasy and then add the creatures, places, and magic that make these worlds unique.",
			"The setting of Call of Cthulhu is a darker version of our world, based on H.P. Lovecraft's observation that, \"The oldest and strongest emotion of mankind is fear, and the strongest kind of fear is fear of the unknown.\".\n\nThe players take the"
					+ " roles of ordinary people, drawn into the realm of the mysterious: detectives, criminals, scholars, artists, war veterans, etc. Often, happenings begin innocently enough, until more and more of the workings behind the scenes are revealed. As the"
					+ " characters learn more of the true horrors of the world and the irrelevance of humanity, their sanity inevitably withers away. To access the tools they need to defeat the horrors - mystic knowledge and magic - the characters must be willing to give up some of their sanity.",
			"The default setting of Blades in the Dark, Doskvol, is a roughly 19th century level of technology combined with urban fantasy. An industrial world under perpetual night, long after some great catastrophe has relegated the sun to storybooks, and resulted in the ghosts of the dead lingering"
					+ " in the world. With players taking the role as members of a criminal organization such as thieves, smugglers, or merchants of some illicit goods. Players grind their way up the criminal underworld by seizing money, territory and infamy.",
			"It’s half past the apocalypse, and things are looking all sorts of directions – down, up, and sideways. Resources are scarce and worth protecting, but this also results in tight-knit communities that want to protect their access to these resources. Earth is barren and ravaged,"
					+ " but this also means that places that aren’t completely devoid of life are that much valuable. And, well, since this is America, two things were left in plenty – guns and oil. And god help anyone trying to stand against the might of the two." };

	private static final String[] tabRules = {
			"## Classes\nThe available classes are:\n* Fighter\n* Wizard\n* Cleric\n* Rogue\n* Ranger\n## Races\nThe races are:\n* Human\n* Elf\n* Dwarf\n* Halfling\n* Gnome\n* Half-orc",
			"You can buy the rule books [here](https://www.chaosium.com/7th-edition-rules/).",
			"# Mechanics\nPlayer characters are chosen from a selection of broad character archetypes each with their own playbook, and each playbook has their own triggers for further character advancement points based on actions taken in play, encouraging the player to play the characters in a way"
					+ " that fits the narrative archetype. For example, the mechanist/alchemist character type the **Leech** receives additional points when addressing their challenges with technical skill, while the fighter character type the **Cutter** receives additional points towards advancement for utilizing violence"
					+ " or threats.\n\nThe players are unified around a shared **crew** or criminal enterprise, having its own communal character sheet. The crew advances of its own in response to the player's abilities to accomplish capers and gain influence in the underworld providing a common set of boons to be invoked"
					+ " by the table. The crew can gain territory, advance in organizational tiers and gain special abilities. Just as the player characters have triggers for gaining advancement based on playing to their character type, the crew too encourages playing in a style befitting the narrative archetype of the"
					+ " criminal organization that they share.\n\nThe basic conflict resolution mechanic is for players to roll a number of six-sided dice equal to the number of points in that character's matching action. A success or failure criterion is always the same and determined by the highest individual number"
					+ " rolled. Special focus is given to the fictional position of the characters to perform a given action, with explicit division of authority between game masters and players giving the players the final say in what character stats are rolled to address a challenge, while the game master is tasked with"
					+ " assessing the strength of the player's position ranging from a dominant to a desperate position. A strong position reduces the potential consequences of a player's failure, while a desperate position offers the players a chance at gaining points towards character advancement.\n\nThe game elides"
					+ " the more granular aspects of combat simulation, instead taking a heavily player-facing approach. Harm generally befalls the players as a consequence of their actions, and little space is given for NPCs to take mechanical initiative. As such NPCs do not require significant stat blocks and there is"
					+ " very little dice rolling on the part of the game master. With no strict distinction between combat or any other task, all difficulty is largely handled in terms of the effect levels determined by the fictional position of the obstacle and players, whether outnumbered or outclassed. Complexity is"
					+ " provided in negotiating the choice of an action to perform, the security of the position that roll has, and the special factors, such as weapon or tool quality, which the player can leverage in the task.",
			"There are five stats – hard (physical strength, intimidation), sharp (intelligence, perception, insight), hot (charisma), cool (stealth, keeping your shit together) and weird (related to the psychic maelstrom). They go from -1 to +3. History is an additional stat, which represents the familiarity"
					+ " (but not necessarily friendliness) between two player characters. It is used for rolling assist / interfere moves.\n\nThe only rolling mechanic in Apocalypse World is the players using moves, rolling 2d6 and adding appropriate modifiers depending on what sort of moves they’re using. Basic moves"
					+ " are available to all characters regardless of their playbook, but every playbook has its own special moves. Basic moves cover general player actions – attacking, surveying a situation or a person and such. Playbook moves are more specialised, in line with the role of specific playbooks. They can"
					+ " also be passive bonuses (+1 to a stat), changing what stat you use for a roll, or give you things like gangs and cars.\n\nThere’s a rudimentary HP system in form of harm. Player characters die upon reaching 6 harm – the first three points of harm represent damage that heals on its own, while the"
					+" final 3 get worse without proper treatment. NPCs generally die upon reaching 3 harm, at MC’s discretion. Armor absorbs harm done, but only if it makes sense – a 2-armor vest won’t do anything against a 3-harm sniper rifle aiming for the head, for instance. Experience is obtained mostly by rolling"
					+" marked stats (each character has two marked stats, one chosen by another player and one chosen by the MC) and by increasing history with another character to +4 (upon which it’s reset back to +1). You gain history by inflicting harm on player characters, by doing certain moves, and as part of"
					+" end-of-session process.\n\nUpon obtaining 5 experience points, you go back to zero and gain an improvement. These are playbook specific and are either bonuses to stats, new playbook moves, moves from other playbooks or things like garages. After sixth improvement, you get advanced improvements"
					+" – a new character (you’re playing two now), retire your current one to safety (which is a rare thing to happen in apocalypse world), change a playbook and such." };

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
