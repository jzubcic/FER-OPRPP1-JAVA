package hr.fer.oprpp1.hw02.prob1;

public class Lexer {

	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state; 

	public Lexer(String text) {
		if (text == null) throw new NullPointerException("Parameter for Lexer constructor must not be null."); 
		data = text.toCharArray(); 
		currentIndex = 0; 
		state = LexerState.BASIC; 
	}
	
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("State must not be null");
		this.state = state; 
	}
	

	/**
	 * Generates and returns next token.
	 * @return next token from lexer
	 * @throws LexerException if there are no more tokens to be generated
	 */
	public Token nextToken() {
		if (data.length < currentIndex) throw new LexerException("There are no more tokens.");
		if (state == LexerState.BASIC) {
			
			ignoreWhitespaces();
			
			if ((data.length == currentIndex + 1) && data[currentIndex] == '\\')
				throw new LexerException("Input cannot end with \\");
			if (data.length == currentIndex) {
				token = new Token(TokenType.EOF, null);
				currentIndex++;
				return token; 
			}
			if (data[currentIndex] == '#') setState(LexerState.EXTENDED);
			if (Character.isLetter(data[currentIndex])) {
				token = wordToken(false); 
				return token; 
			}
			
			if (data[currentIndex] == '\\') {
				currentIndex++;
				token = wordToken(true); 
				return token; 
			}
			
			if (Character.isDigit(data[currentIndex])) {
				token = numberToken(); 
				return token; 
			}
			
			
			
			if (!Character.isDigit(data[currentIndex]) && !Character.isLetter(data[currentIndex])
					&& !(data[currentIndex] == '\\')) {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
				return token;
			}
			return null; 
			
		} else {
			
			ignoreWhitespaces();
			
			if (currentIndex == data.length) {
				currentIndex++;
				token = new Token(TokenType.EOF, null); 
				return token; 
			}
			

			String currentString = new String(); 
			while (currentIndex < data.length && (data[currentIndex] != ' ' && data[currentIndex] != '#')) {
				currentString += data[currentIndex++];
			} 
			
			if (currentIndex < data.length && data[currentIndex] == '#') setState(LexerState.BASIC); 
			if (!currentString.equals("")) {
				token = new Token(TokenType.WORD, currentString);
			} else {
				if (currentIndex < data.length && data[currentIndex] == '#') {
					token = new Token(TokenType.SYMBOL, '#'); 
					currentIndex++;
				}
			}
			return token; 
		}
	}
	
	/**
	 * Returns last generated token; does not generate new token.
	 * @return last generated token
	 */
	public Token getToken() {
		return token; 
	}
	
	private Token wordToken(boolean backslashBefore) {
		String currentString = new String(); 
		if (backslashBefore && Character.isLetter(data[currentIndex])) 
			throw new LexerException("Letters cannot follow \\.");
		while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) ||
				(Character.isDigit(data[currentIndex]) && backslashBefore ||
				 data[currentIndex] == '\\'))) {
			if (data[currentIndex] == '\\' && !backslashBefore) {
				currentIndex++;
				backslashBefore = true; 
			} else {
				currentString += data[currentIndex++];  
				backslashBefore = false;
			}			 
		}
		return new Token(TokenType.WORD, currentString); 
	}
	
	private Token numberToken() {
		String currentString = new String(); 
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentString += data[currentIndex++]; 
		}
		try {
			Long result = Long.parseLong(currentString);
		} catch (NumberFormatException e) {
			throw new LexerException("Number is too big."); 
		}
		return new Token(TokenType.NUMBER, Long.parseLong(currentString));
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
