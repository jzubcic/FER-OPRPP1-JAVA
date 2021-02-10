package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {

	private IFieldValueGetter fieldGetter; 
	private String stringLiteral; 
	private IComparisonOperator comparisonOperator;
	
	public ConditionalExpression() {
		super(); 
	}
	
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public void setFieldGetter(IFieldValueGetter fieldGetter) {
		this.fieldGetter = fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public void setStringLiteral(String stringLiteral) {
		this.stringLiteral = stringLiteral;
	}

	public void setFieldValueGetter(IFieldValueGetter fieldValueGetter) {
		this.fieldGetter = fieldValueGetter;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	public void setComparisonOperator(IComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	} 
	
	
}
