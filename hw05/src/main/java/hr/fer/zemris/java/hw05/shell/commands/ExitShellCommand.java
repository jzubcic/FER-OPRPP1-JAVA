package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class ExitShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>(); 
	
	public ExitShellCommand() {
		instructions.add("Command exits MyShell."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit"; 
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

}
