package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {

	List<String> instructions = new ArrayList<>();
	
	public HelpShellCommand() {
		instructions.add("Can be called with a command name as argument or no argument.");
		instructions.add("If called with no arguments it lists all available commands."); 
		instructions.add("If called with an argument it gives description of given command."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments == null) {
			env.writeln("Listing all available commands:");
			for (String commandName : env.commands().keySet()) {
				env.writeln(commandName);
			}
		} else {
			ShellCommand command = env.commands().get(arguments); 
			env.writeln(command.getCommandName());
			for (String instruction : command.getCommandDescription()) {
				env.writeln(instruction);
			}
		}
		
		return ShellStatus.CONTINUE; 
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

}
