package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;


public class Lexer {

	private LexerState state;
	private char[] data; 
	private Token token;
	private int currentIndex; 
	
	public Lexer(String text) {
		if (text == null) throw new NullPointerException("Parameter must not be null."); 
		data = text.toCharArray(); 
		currentIndex = 0; 
		state = LexerState.TEXT; 
	}
	
	/**
	 * Sets state of Lexer to given state.
	 * @param state new state of lexer
	 * @throws NullPointerException if state is <code>null</code>
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("State must not be null");
		this.state = state;
	}
	
	/**
	 * Method returns next token from lexer's text.
	 * @return next token 
	 */
	public Token nextToken() {
		 if (data.length < currentIndex) throw new LexerException("There are no more tokens.");
		 
		 if (state == LexerState.TEXT) {
			 String currentString = new String(); 
			 if (data.length == currentIndex) {
					token = new Token(TokenType.EOF, null);
					currentIndex++;
					return token; 
			 }
			 
			 for (int i = currentIndex; i < data.length; i++) {
				 if (data[i] == '{' && data[i+1] == '$') {
					 setState(LexerState.TAG);
					 break; 
				 }
				 currentString += data[i];
				 currentIndex++;
			 }
			 token = new Token(TokenType.TEXT, currentString);
			 return token;
		 } else {
			 String workingString = new String(); 
			 
			 if (data.length == currentIndex) {
					token = new Token(TokenType.EOF, null);
					currentIndex++;
					return token; 
			 }
			 
			 if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				 token = new Token(TokenType.TAG_START, "{$");
				 currentIndex += 2; 
				 return token; 
			 }
			 
			 ignoreWhitespaces();
			 
			 if (data[currentIndex] == '=' && data[currentIndex-1] == '$') {
				 token = new Token(TokenType.TAGNAME, '='); 
				 currentIndex++;
				 return token; 
			 }
			 
			 if (Character.isLetter(data[currentIndex]) && data[currentIndex-1] == '$') {
				 while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '_' 
						 || Character.isDigit(data[currentIndex])) {
					 workingString += data[currentIndex++]; 
				 }
				 token = new Token(TokenType.TAGNAME, workingString);
				 return token;
			 } else {
				 if (data[currentIndex-1] == '$' && !Character.isLetter(data[currentIndex])) throw new LexerException("Tag name is invalid."); 
			 }
			 
			 if (Character.isLetter(data[currentIndex])) {
				 while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '_' 
						 || Character.isDigit(data[currentIndex])) {
					 workingString += data[currentIndex++]; 
				 }
				 token = new Token(TokenType.VARIABLE, workingString);
				 return token;
			 }
			 
			 if (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' &&
					 Character.isDigit(data[currentIndex+1]))) {
				 if (data[currentIndex] == '-') {
					 workingString += '-';
					 currentIndex++;
				 }
				 while (Character.isDigit(data[currentIndex])) {
					 workingString += data[currentIndex++]; 
				 }
				 token = new Token(TokenType.NUMBER, workingString); 
				 return token; 
			 }
			 
			 if (data[currentIndex] == '@' && (Character.isDigit(data[currentIndex+1])
					 || Character.isLetter(data[currentIndex+1]) || data[currentIndex+1] == '_')) {
				 while (Character.isDigit(data[currentIndex+1])
						 || Character.isLetter(data[currentIndex+1])
						 || data[currentIndex+1] == '_') {
					 workingString += data[currentIndex++]; 
				 }
				 token = new Token(TokenType.FUNCTION, workingString);
				 return token; 
			 }
			 
			 if (data[currentIndex] == '$' && data[currentIndex+1] == '}') {
				 token = new Token(TokenType.TAG_END, "$}");
				 currentIndex += 2; 
				 setState(LexerState.TEXT); 
				 return token; 
			 }
			 
			 if (data[currentIndex] == '\"') {
				 currentIndex++;
				 workingString += '\"';
				 while (data[currentIndex] != '\"') {
					 workingString += data[currentIndex++]; 
				 }
				 workingString += '\"';
				 token = new Token(TokenType.STRING, workingString); 
				 return token; 
			 }
			 
			 if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '/' || data[currentIndex] == '*') {
				 token = new Token(TokenType.OPERATOR, data[currentIndex++]);
				 return token; 
			 }
		 }
		 
		 return null;
		 
	}
	
	/**
	 * Returns last generated token
	 * @return last generated token
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
