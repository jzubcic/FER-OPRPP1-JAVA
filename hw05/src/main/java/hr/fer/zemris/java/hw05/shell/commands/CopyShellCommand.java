package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>(); 
	
	public CopyShellCommand() {
		instructions.add("Command takes two arguments: source file name and destination file name.");
		instructions.add("If destination is given as directory, source file name will be used as destination file name.");
		instructions.add("The command works exclusively with files."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] args = new String[2]; 
		if (arguments.contains("\"")) {
			boolean foundQuotation = false; 
			StringBuilder sb = new StringBuilder();
			int counter = 0;  
			for (int i = 0; i < arguments.length(); i++) {			
				if (arguments.charAt(i) == ' ' && !foundQuotation && !sb.isEmpty()) {
					args[counter++] = sb.toString(); 
					foundQuotation = false;
					sb = new StringBuilder(); 
				} else if (arguments.charAt(i) == '\"' && !foundQuotation) {
					foundQuotation = true; 
				} else if (foundQuotation && arguments.charAt(i) == '\"'){
					args[counter++] = sb.toString();
					foundQuotation = false; 
					sb = new StringBuilder(); 
				} else if (i == arguments.length() - 1) {
					sb.append(arguments.charAt(i)); 
					args[counter] = sb.toString();
				} else if (arguments.charAt(i) == ' ' && sb.isEmpty()) {
	
				} else {			
					sb.append(arguments.charAt(i)); 
				}
				
			}
		} else {
			args = arguments.split("\\s+"); 
		}
		
		if (Files.isDirectory(Paths.get(args[1]))) {
			args[1] += "\\" + Paths.get(args[0]).getFileName().toString();
		}

		try(FileInputStream in = new FileInputStream(args[0]);
			FileOutputStream out = new FileOutputStream(args[1])) {
			byte[] byteArray = new byte[4096]; 
			
			int noOfBytes; 
			while ((noOfBytes = in.read(byteArray)) > - 1) {
				out.write(byteArray, 0, noOfBytes);
			}
		} catch (FileNotFoundException e) {
			throw new ShellIOException("Error while copying file.");
		} catch (IOException e) {
			throw new ShellIOException("Error while copying file.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy"; 
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

}
