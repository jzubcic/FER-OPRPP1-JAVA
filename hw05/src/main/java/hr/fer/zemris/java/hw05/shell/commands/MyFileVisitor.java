package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

import hr.fer.zemris.java.hw05.shell.Environment;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

	private static int level = 0;
	private Environment env; 
	
	public MyFileVisitor(Environment env) {
		this.env = env; 
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path file, IOException exc) {
		level--;
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
		env.writeln(" ".repeat(level * 2) + file.getFileName());
		level++;
		return FileVisitResult.CONTINUE;
	}
}
