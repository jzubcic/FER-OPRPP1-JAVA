package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

public class LsShellCommand implements ShellCommand {

	private List<String> instructions = new ArrayList<>();
	
	public LsShellCommand() {
		instructions.add("Command writes directory listing.");
		instructions.add("Command takes a single argument, the directory."); 
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.replaceAll("\"", "");
		Path dir = Paths.get(arguments); 
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
						entry, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
						);
				BasicFileAttributes attributes = faView.readAttributes();
				
				
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				if (attributes.isDirectory()) {
					env.write("d");
				} else {
					env.write("-");
				}
				
				
				if (Files.isReadable(entry)) {
					env.write("r");
				} else {
					env.write("-");
				}
				
				if (Files.isWritable(entry)) {
					env.write("w");
				} else {
					env.write("-");
				}
				
				if (Files.isExecutable(entry)) {
					env.write("x");
				} else {
					env.write("-");
				}
				
				long fileSize = attributes.size();		
				env.write(" ".repeat(10 - String.valueOf(fileSize).length()));
				env.write(String.valueOf(fileSize));
				env.write(" ");
				
				env.write(formattedDateTime + " ");
				
				env.writeln(entry.getFileName().toString());
				
			}
		} catch (IOException e) {
			throw new ShellIOException("Error while listing directory.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(instructions); 
	}

}
