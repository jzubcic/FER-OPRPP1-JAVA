package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand{

	List<String> instructions = new ArrayList<>();
	
	public MkdirShellCommand() {
		instructions.add("This command is used for creating a directory.");
		instructions.add("It takes a single argument: directory name,"
						+ "and creates the appropriate directory structure.");
		instructions.add("Path can be written with or without \"\" quotes.");
	}
	
	//pripaziti na stringove zadane s razmakom
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.replaceAll("\"", "");
		Path path = Paths.get(arguments); 
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new ShellIOException("Error while making directory");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions); 
	}

}
