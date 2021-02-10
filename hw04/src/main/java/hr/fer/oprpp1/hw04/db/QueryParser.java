package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
	
	private String line; 
	private List<ConditionalExpression> expressions = new ArrayList<>(); 
	
	public QueryParser(String line) {
		this.line = line; 
		parse(); 
	}

	/**
	 * Returns true if query is of type "jmbag="xxxxxxx"", otherwise false
	 */
	public boolean isDirectQuery() {
		if (expressions.size() == 1) {
			if (expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
					expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
				return true; 
			}
		}
		return false; 
	}
	
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) throw new IllegalStateException("Query must be direct."); 
		
		return expressions.get(0).getStringLiteral();
	}
	
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Parses query by calling Lexer's nextToken method and then adding 
	 * ConditionalExpressions into a list. 
	 */
	public void parse() {
		QueryLexer lexer = new QueryLexer(line);
		Token[] tokens = new Token[3]; 
		tokens[0] = lexer.nextToken();

		if (tokens[0].getValue().equals("AND")) {
			parse(); 
			return; 
		}
		
		tokens[1] = lexer.nextToken(); 
		tokens[2] = lexer.nextToken(); 
		
		IFieldValueGetter getter = null; 
		ConditionalExpression expression = new ConditionalExpression(); 
		switch (tokens[0].getValue()) {
			case "firstName":  
				getter = FieldValueGetters.FIRST_NAME;
				break;
			case "lastName":
				getter = FieldValueGetters.LAST_NAME;
				break;
			case "jmbag":
				getter = FieldValueGetters.JMBAG;
				break;
		}
		
		IComparisonOperator operator = null;
		switch (tokens[1].getValue()) {
			case "<":
				operator = ComparisonOperators.LESS; 
				break;
			case ">":
				operator = ComparisonOperators.GREATER;
				break;				
			case "<=":
				operator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case ">=":
				operator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "=":
				operator = ComparisonOperators.EQUALS;
				break;
			case "!=":
				operator = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE": 
				operator = ComparisonOperators.LIKE;
				break; 
		}
		
		String stringLiteral = tokens[2].getValue();
		
		expressions.add(new ConditionalExpression(getter, stringLiteral, operator));
	}
	
	
}
