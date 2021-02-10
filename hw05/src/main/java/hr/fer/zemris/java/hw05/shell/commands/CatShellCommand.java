package hr.fer.zemris.java.hw05.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CatShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>(); 
	
	public CatShellCommand() {
		instructions.add("Command takes two arguments, a path to some file (mandatory)"
						+ "and charset name that should be used to interpret chars from bytes.");
		instructions.add("If second argument is not provided, the default platform charset will be used."); 
		instructions.add("The command opens the given file and writes its content to console."); 
		
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
 
		Charset cs = Charset.defaultCharset();
		if (args.length == 2) {
			cs = Charset.forName(args[1]); 
		}
		
		try (FileInputStream in = new FileInputStream(args[0]);
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			byte[] byteArray = new byte[4096]; 
			
			int noOfBytes; 
			while ((noOfBytes = in.read(byteArray)) > - 1) {
				out.write(byteArray, 0, noOfBytes);
			}
			env.writeln(out.toString(cs));
		} catch (FileNotFoundException e) {
			throw new ShellIOException("Error while performing cat command.");
		} catch (IOException e) {
			throw new ShellIOException("Error while performing cat command.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat"; 
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions); 
	}

}
