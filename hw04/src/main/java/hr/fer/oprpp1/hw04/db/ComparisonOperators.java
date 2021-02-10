package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {

	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0; 
	};
	
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0; 		
	};
	
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0; 
	};
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0; 
	};
	
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) == 0;
	};
	
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) != 0; 
	};
	
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		String[] strings = value2.split("\\*"); 

		if (!value1.substring(0, strings[0].length()).equals(strings[0])) {
			return false; 
		}
		
		if (strings.length != 1) {
			if (!value1.substring(value1.length() - strings[1].length(), value1.length()).equals(strings[1])) {
				return false; 
			}
		}
		return true; 
	};
}
