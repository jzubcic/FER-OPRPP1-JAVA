package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {

	private List<StudentRecord> students = new ArrayList<>();
	private Map<String, Integer> jmbagIndex = new HashMap<>(); 
	
	public StudentDatabase(List<String> list) {
		int counter = 0; 
		for (String s : list) {
			String[] strings = s.split("\\s+");
			if (strings.length == 4 && (jmbagIndex.containsKey(strings[0]) || Integer.parseInt(strings[3]) < 1
					|| Integer.parseInt(strings[3]) > 5)) {
				throw new IllegalArgumentException(); 
			}
			
			if (strings.length == 5 && (jmbagIndex.containsKey(strings[0]) || Integer.parseInt(strings[4]) < 1
					|| Integer.parseInt(strings[4])> 5)) {
				throw new IllegalArgumentException(); 
			}
			
			StudentRecord student = null; 
			if (strings.length == 5) {
				String lastName = strings[1] + " " + strings[2]; 
				student = new StudentRecord(strings[0],
						lastName ,strings[3], strings[4]);
				
			} else {
				student = new StudentRecord(strings[0],
					strings[1], strings[2], strings[3]);
			}
			students.add(student); 
			jmbagIndex.put(strings[0], counter++);
		}
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		if (!jmbagIndex.containsKey(jmbag)) return null; 
		return students.get(jmbagIndex.get(jmbag)); 
	}
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>(); 
		for (StudentRecord student : students) {
			if (filter.accepts(student)) {
				filteredList.add(student); 
			}
		}
		return filteredList; 
	}
}
