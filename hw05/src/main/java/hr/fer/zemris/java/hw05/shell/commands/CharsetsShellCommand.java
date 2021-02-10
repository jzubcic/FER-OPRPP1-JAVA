package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>(); 
	
	public CharsetsShellCommand() {
		instructions.add("Command lists all supported charsets for current platform."); 
		instructions.add("Command takes no arguments.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (String str : Charset.availableCharsets().keySet()) {
			env.writeln(str);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions); 
	}

}
