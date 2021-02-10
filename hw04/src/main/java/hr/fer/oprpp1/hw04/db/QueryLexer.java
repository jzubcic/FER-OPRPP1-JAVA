package hr.fer.oprpp1.hw04.db;

public class QueryLexer {


	private char[] data; 
	private Token token; 
	private int currentIndex; 
	
	public QueryLexer(String line) {
		if (line == null) throw new NullPointerException("Line must not be null."); 
		
		currentIndex = 0; 
		data = line.toCharArray(); 
	}
	
	
	/**
	 * Calculates next token, sets it as current token and returns it. 
	 * @return next token
	 */
	public Token nextToken() {
		if (data.length < currentIndex) throw new NullPointerException("There are no more tokens.");
		
		String currentString = new String(); 
		
		if (data.length == currentIndex) {
			token = new Token(TokenType.EOF, null); 
			currentIndex++;
			return token; 
		}
		
		ignoreWhitespaces();
		
		if (data[currentIndex] == '\"') {
			int starCount = 0; 
			currentIndex++;
			while (data[currentIndex] != '\"') {
				if (data[currentIndex] == '*') {
					if (++starCount == 2) {
						throw new LexerException("Only one * can be in value.");
					}
				}
				currentString += data[currentIndex++]; 
			}
			currentIndex++;
			token = new Token(TokenType.VALUE, currentString);
			return token; 
		}
		

		if (Character.isLetter(data[currentIndex])) {
			while (Character.isLetter(data[currentIndex])) {
				currentString += data[currentIndex++]; 
			}
			
			if (currentString.equals("LIKE")) {
				token = new Token(TokenType.OPERATOR, currentString); 
				return token; 
			}
			
			if (currentString.toUpperCase().equals("AND")) {
				token = new Token(TokenType.AND, currentString.toUpperCase()); 
				return token; 
			}
			
			if (currentString.equals("jmbag") || currentString.equals("firstName") ||
				currentString.equals("lastName") || currentString.equals("finalGrade")) {
				token = new Token(TokenType.ATTRIBUTE, currentString); 
				return token; 
			}
			
			throw new LexerException(currentString + " is invalid token.");
				
		}
		

		if (data[currentIndex] == '<' || data[currentIndex] == '>'
				|| data[currentIndex] == '=' || data[currentIndex] == '!') {
			if ((data[currentIndex] != '=' || data[currentIndex] == '!' ) && data[currentIndex + 1] == '=') {
				currentString += data[currentIndex]; 
				currentString += data[++currentIndex]; 
			} else {
				currentString += data[currentIndex++]; 
			}
			token = new Token(TokenType.OPERATOR, currentString); 
			return token; 
		}
		return null; 
	}
	
	/**
	 * Returns current token. 
	 * @return current token
	 */
	public Token getToken() {
		return token; 
	}
	
	private void ignoreWhitespaces() {
		for (int i = currentIndex; i < data.length; i++) {
			if (String.valueOf(data[i]).equals(" ") || data[i] == '\n' ||
					data[i] == '\t' || data[i] == '\r') {
				currentIndex++;
			} else {
				break; 
			}			
		}
	}
	
}
