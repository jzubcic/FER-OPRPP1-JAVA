package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>();
	
	public TreeShellCommand() {
		instructions.add("Command prints tree for given directory.");
		instructions.add("Command takes a single argument, directory."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		MyFileVisitor fileVisitor = new MyFileVisitor(env);
		arguments = arguments.replaceAll("\"", ""); 
		
		env.writeln(arguments);
		try {
			Files.walkFileTree(Paths.get(arguments), fileVisitor);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "tree"; 
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

}
