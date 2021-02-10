package hr.fer.zemris.java.hw05.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.crypto.Util;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class HexdumpShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>(); 
	
	public HexdumpShellCommand() {
		instructions.add("Command produces hex-output of file.");
		instructions.add("Command takes a single argument, a file name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.replaceAll("\"", "");
		
		try (FileInputStream in = new FileInputStream(arguments)) {
			byte[] byteArray = new byte[16]; 
			int counter = 0; 
			
			while (in.read(byteArray) != -1) {
				env.write(String.format("%08x", counter) + ": ");
				env.write(splitHex(Util.byteToHex(byteArray)));
				env.writeln(printText(Util.byteToHex(byteArray)));
				
				counter += 16; 
			}		
			
		} catch (FileNotFoundException e) {
			throw new ShellIOException("Error while performing hexdump.");
		} catch (IOException e) {
			throw new ShellIOException("Error while performing hexdump.");
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions);
	}

	private String splitHex(String hexString) {
		StringBuilder sb = new StringBuilder(); 
		for (int i = 0; i < hexString.length(); i+=2) {
			sb.append(hexString.charAt(i)); 
			sb.append(hexString.charAt(i+1));
			if (i != 14) {
				sb.append(" ");
			} else {
				sb.append("|"); 
			}
		}
		
		sb.append("| "); 
		return sb.toString();
	}
	
	private String printText(String hexString) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < hexString.length(); i+=2) {
			StringBuilder sb = new StringBuilder(); 
			sb.append(hexString.charAt(i));
			sb.append(hexString.charAt(i+1));
			
			byte[] b = Util.hexToByte(sb.toString()); 
			if (b[0] > 127 || b[0] < 32) {
				result.append("."); 
			} else {
				result.append(new String(b, StandardCharsets.UTF_8));
			}
		}
		return result.toString(); 
	}
}
