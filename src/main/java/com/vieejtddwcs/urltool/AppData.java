package com.vieejtddwcs.urltool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AppData {

	public static final String CONFIG_FILE_PATH =
			System.getProperty("user.home") + File.separator + "url-tool.properties";
	public static String dataFolder = System.getProperty("user.home");

	private final String dataFileName;
	private final Set<String> dataURLs;
	private String outputURLs;
	private String outputURLsFull;
	
	public AppData(String dataFileName) {
		this.dataFileName = dataFileName;
		dataURLs = new HashSet<>();
		loadDataURLs();
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public Set<String> getDataURLs() {
		return dataURLs;
	}

	public String getOutputURLs() {
		return outputURLs;
	}

	public void setOutputURLs(String outputURLs) {
		this.outputURLs = outputURLs;
	}

	public String getOutputURLsFull() {
		return outputURLsFull;
	}

	public void setOutputURLsFull(String outputURLsFull) {
		this.outputURLsFull = outputURLsFull;
	}
	
	private void loadDataURLs() {
		String filePath = dataFolder + File.separator + dataFileName + ".txt";
		try (
			BufferedReader br = new BufferedReader(new FileReader(filePath));
		) {
			String url;
			while ((url = br.readLine()) != null && !url.replaceAll("\\s+", "").isEmpty()) {
				dataURLs.add(url.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}