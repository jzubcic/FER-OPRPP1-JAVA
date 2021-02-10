package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) {
		List<String> lines = new ArrayList<>(); 
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StudentDatabase studentDB = new StudentDatabase(lines); 
		
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine(); 
		
		while (!line.equals("exit")) {
			if (!line.substring(0, 5).equals("query")) throw new IllegalArgumentException("Invalid command name."); 
			
			QueryParser parser = new QueryParser(line.substring(5)); 
			QueryFilter queryFilter = new QueryFilter(parser.getQuery());
			List<StudentRecord> students = studentDB.filter(queryFilter); 
			
			int longestLastName = 0; 
			int longestFirstName = 0;
			for (StudentRecord student : students) {
				if (student.getLastName().length() > longestLastName) {
					longestLastName = student.getLastName().length();
				}
				
				if (student.getFirstName().length() > longestFirstName) {
					longestFirstName = student.getFirstName().length();
				}
			}
			
			System.out.print("+============+");
			for (int i = 0; i < longestLastName + 2; i++) System.out.print("=");
			System.out.print("+");
			for (int i = 0; i < longestFirstName + 2; i++) System.out.print("=");
			System.out.print("+===+");
			System.out.println();
			
			for (StudentRecord student : students) {
				System.out.print("| " + student.getJmbag() + " | ");
				System.out.print(student.getLastName());
				for (int i = student.getLastName().length(); i < longestLastName + 1; i++) System.out.print(" ");
				System.out.print("| ");
				System.out.print(student.getFirstName());
				for (int i = student.getFirstName().length(); i < longestFirstName + 1; i++) System.out.print(" ");
				System.out.print("| " + student.getFinalGrade() + " |"); 
				System.out.println();
			}
			
			System.out.print("+============+");
			for (int i = 0; i < longestLastName + 2; i++) System.out.print("=");
			System.out.print("+");
			for (int i = 0; i < longestFirstName + 2; i++) System.out.print("=");
			System.out.print("+===+");
			System.out.println();
			System.out.println("Records selected: " + students.size());
			
			line = sc.nextLine();
		}
		
	}
	
	
}
