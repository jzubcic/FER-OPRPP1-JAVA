package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	private List<ConditionalExpression> list; 
	
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list; 
	}

	/**
	 * Returns false if given StudentRecord does not satisfy at least one of 
	 * the ConditionalExpressions in list, otherwise true.
	 * @param record StudentRecord to be tested
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : list) {
			if (!expression.getComparisonOperator().satisfied(
					expression.getFieldGetter().get(record), 
					expression.getStringLiteral()
				)) return false; 
		}
		
		return true;
	}
}
