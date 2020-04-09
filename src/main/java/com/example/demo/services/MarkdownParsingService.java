package com.example.demo.services;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;

public class MarkdownParsingService {
	
	private MarkdownParsingService() {
	}

	private static Parser parser = Parser.builder().build();
	private static HtmlRenderer htmlRenderer = HtmlRenderer.builder().escapeHtml(true).build();

	public static String parse(String markdown) {
		Node node = parser.parse(markdown);
		
		return htmlRenderer.render(node);
	}
	
	public static void parse(Rpg rpg) {
		rpg.setDescription(parse(rpg.getDescription()));
		rpg.setRules(parse(rpg.getRules()));
		for (Scenario scenario : rpg.getScenarios()) {
			parse(scenario);
		}
	}
	
	public static void parse(Scenario scenario) {
		scenario.setDescription(parse(scenario.getDescription()));
		scenario.setQuests(parse(scenario.getQuests()));
	}
}
