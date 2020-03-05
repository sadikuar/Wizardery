package com.example.demo.services;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParsingService {

	private static Parser parser = Parser.builder().build();
	private static HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

	public static String parse(String markdown) {
		Node node = parser.parse(markdown);
		return htmlRenderer.render(node);
	}
}
