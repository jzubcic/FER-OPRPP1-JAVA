package hr.fer.oprpp1.hw04.db;

public class Token {

	private TokenType type; 
	private String value; 
	
	public Token(TokenType type, String value) {
		this.type = type; 
		this.value = value; 
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
	
}
