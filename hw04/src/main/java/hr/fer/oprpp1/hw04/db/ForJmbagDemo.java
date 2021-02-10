package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class ForJmbagDemo {

	
	public static void main(String[] args) {
		List<String> students = new ArrayList<String>(); 
		students.add("0000000047	Rakipović	Ivan	4");
		students.add("0000000041	Orešković	Jakša	2");
		students.add("0000000015	Glavinić Pecotić	Kristijan	4");
		
		StudentDatabase studentDB = new StudentDatabase(students); 
		//System.out.println(studentDB.forJMBAG("0000000047").getLastName());
		//System.out.println(studentDB.forJMBAG("0000000015").getLastName());
		/*IFilter filterTrue = record -> true;
		IFilter filterFalse = record -> false;*/
		//List<StudentRecord> listOfStudents = studentDB.filter(filterTrue); 
		/*for (StudentRecord stud : listOfStudents) {
			System.out.println(stud.getLastName());
		}*/
		
		/*listOfStudents = studentDB.filter(filterFalse); 
		System.out.println(listOfStudents.size());*/
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		StudentRecord record = studentDB.forJMBAG("0000000015");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		);

	}
}
