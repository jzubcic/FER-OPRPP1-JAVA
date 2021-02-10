package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class SmartScriptParser {

	private Lexer lexer;
	private DocumentNode documentNode;
	private String text; 
	private ObjectStack stack; 
	
	public DocumentNode getDocumentNode() {
		return documentNode; 
	}
	
	public SmartScriptParser(String text) {
		this.text = text; 
		lexer = new Lexer(text);
		stack = new ObjectStack(); 
		parse(); 
	}
	
	
	public void parse() {
		documentNode = new DocumentNode(); 
		stack.push(documentNode);
		
		while (!lexer.nextToken().getType().equals(TokenType.EOF)) {
			if (lexer.getToken().getType().equals(TokenType.TEXT)) {
				TextNode textNode = new TextNode((String) lexer.getToken().getValue());
				((Node) stack.peek()).addChildNode(textNode); 
			}
			
			if (lexer.getToken().getType().equals(TokenType.TAG_START)) {
				if (((String) lexer.nextToken().getValue()).toUpperCase().equals("FOR")) {
					ForLoopNode forLoopNode = new ForLoopNode(); 
					stack.push(forLoopNode); 
				}
			}
			
			
		}
	}
	
}
