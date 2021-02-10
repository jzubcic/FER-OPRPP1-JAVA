package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeShellCommand;

public class MyShell {
	
	static class MyEnvironment implements Environment {
		private Scanner sc = new Scanner(System.in);
		private Character promptSymbol; 
		private Character multilineSymbol; 
		private Character moreLinesSymbol; 
		private SortedMap<String, ShellCommand> commands = new TreeMap<>();
		
		public MyEnvironment() {			 
			commands.put("exit", new ExitShellCommand());
			commands.put("ls", new LsShellCommand());
			commands.put("tree", new TreeShellCommand()); 
			commands.put("copy", new CopyShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("hexdump", new HexdumpShellCommand());
			commands.put("charsets", new CharsetsShellCommand());
			commands.put("help",  new HelpShellCommand());
			commands.put("symbol", new SymbolShellCommand()); 
			commands.put("cat",  new CatShellCommand());
		}
		
		@Override
		public String readLine() throws ShellIOException {
			if (sc.hasNextLine()) {
				return sc.nextLine(); 
			} 
			return null; 
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);			
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands); 
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol; 
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol; 			
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol; 
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			moreLinesSymbol = symbol; 
		}
		
	}

	public static void main(String[] args) { 
		System.out.println("Welcome to MyShell v 1.0");
		
		MyEnvironment myEnvironment = new MyEnvironment(); 
		myEnvironment.setMorelinesSymbol('\\');
		myEnvironment.setMultilineSymbol('|');
		myEnvironment.setPromptSymbol('>');
		ShellStatus status = ShellStatus.CONTINUE;
		
		do {
			myEnvironment.write(String.valueOf(myEnvironment.getPromptSymbol()) + " ");
			String line = myEnvironment.readLine();
			StringBuilder sb = new StringBuilder(); 
			
			while(line.endsWith(String.valueOf(myEnvironment.getMorelinesSymbol()))) {
				sb.append(line.substring(0, line.length() - 1)); 
				myEnvironment.write(String.valueOf(myEnvironment.getMultilineSymbol()) + " ");
				line = myEnvironment.readLine(); 
			}
			
			if (!sb.isEmpty()) {
				sb.append(line); 
				line = sb.toString();
			}
			
			String[] strs = line.split("\\s+"); 
			String commandName = strs[0]; 
			String arguments = null; 
			if (strs.length > 1) {
				arguments = line.substring(strs[0].length() + 1); 
			}

			ShellCommand command = myEnvironment.commands().get(commandName);
			status = command.executeCommand(myEnvironment, arguments); 
	
		} while (status != ShellStatus.TERMINATE); 
		
	}
}
