package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>();
	
	public SymbolShellCommand() {
		instructions.add("Command takes one or two arguments.");
		instructions.add("First argument is either PROMPT, MORELINES or MULTILINE."); 
		instructions.add("Second argument is a character and is optional."); 
		instructions.add("If second argument is not given, command writes current symbol for first argument."); 
		instructions.add("If second argument is give, the symbol for first argument is replaced by second argument."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+"); 
		if (args.length == 1) {
			switch (args[0]) {
				case "PROMPT": 
					env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() +
							"'");
					break; 
				case "MORELINES": 
					env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() +
							"'");
					break; 
				case "MULTILINE":
					env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() +
							"'");
					break;
			}
		} else {
			switch (args[0]) {
				case "PROMPT": 				
					env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + args[1] + "'");
					env.setPromptSymbol(args[1].charAt(0));
					break; 
				case "MORELINES": 
					env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + args[1] + "'");
					env.setMorelinesSymbol(args[1].charAt(0));
					break;  
				case "MULTILINE":
					env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + args[1] + "'");
					env.setMultilineSymbol(args[1].charAt(0));
					break; 
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

}
