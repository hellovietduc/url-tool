package com.vieejtddwcs.urltool;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileUtils {

	private FileUtils() {
		throw new AssertionError();
	}
	
	/**
	 * Get the filename extension from the given filename.
	 * 
	 * @param fileName - the given filename
	 * @return the filename extension, null if fileName is
	 * null or doesn't contain extension
	 */
	public static String getFileExtension(String fileName) {
		if (fileName == null) return null;
		Pattern pattern = Pattern.compile("\\.[a-zA-Z0-9]+$");
		Matcher matcher = pattern.matcher(fileName);
		return matcher.find() ? matcher.group() : null;
	}
	
	/**
	 * Remove the filename extension from the given absolute filename.
	 * 
	 * @param fileName - the given filename
	 * @return the filename part of the absolute filename, fileName if
	 * it has no extension, or null if the given filename is null
	 */
	public static String removeFileExtension(String fileName) {
		if (fileName == null) return null;
		String extension = getFileExtension(fileName);
		return extension == null ?
				fileName : fileName.substring(0, fileName.length() - extension.length());
	}
	
	/**
	 * Get a list of names of text files (.txt) found in this folder.
	 * 
	 * @param folder - the folder to look for files
	 * @return a List of String contains names of text files found
	 * @throws NullPointerException if the folder doesn't exist
	 */
	public static List<String> getTextFileNames(String folder) {
		File[] fileList = Objects.requireNonNull(new File(folder).listFiles());
		return Arrays.asList(fileList).stream()
				.filter(file -> file.isFile())
				.filter(file -> ".txt".equalsIgnoreCase(getFileExtension(file.getName())))
				.map(file -> file.getName())
				.collect(Collectors.toList());
	}
	
	/**
	 * Tell if a filename is valid.
	 * A filename can't contain any of the following characters:
	 * \ / : * ? < > |
	 * 
	 * @param fileName - the fileName to be checked
	 * @return false if fileName is null, empty or contains
	 * invalid characters, else return true
	 */
	public static boolean isFileNameValid(String fileName) {
		if (fileName == null || fileName.replaceAll("\\s+", "").isEmpty()) return false;
		Pattern pattern = Pattern.compile("[\\\\/:*?\"<>|]");
		Matcher matcher = pattern.matcher(fileName);
		return !matcher.find();
	}
	
}