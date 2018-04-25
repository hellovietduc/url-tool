package com.vieejtddwcs.urltool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import org.jsoup.nodes.Document;

public class Controller implements ActionListener, ItemListener {
	
	private final View view;
	private AppData appData;
	
	public Controller(View view) {
		this.view = view;
		
		changeDataFolder();
		loadDataFiles();
		
		if (view.getDataFiles().size() == 0) createNewDataFile();
		else switchDataFile(view.getSelectedDataFile());
		
		view.setTitle(AppData.dataFolder + " - URL Tool");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (appData != null) {
			// Functions that need initialized appData
			switch (e.getActionCommand()) {
				case Commands.SAVE_DATA_FILE:
					saveDataFile();
					break;
				case Commands.CHECK_INPUT_URLS:
					checkInputURLs(view.getInputURLs());
					break;
				case Commands.CHECK_INPUT_FROM_FILE:
					checkInputURLsFromFile();
					break;
				case Commands.GET_GOOGLE_URLS:
					getGoogleURLs();
					break;
				case Commands.CLEAR_APP_DATA:
					clearAppData();
					break;
			}
		}
		
		// Functions that don't need initialized appData
		switch (e.getActionCommand()) {
			case Commands.CREATE_NEW_DATA_FILE:
				createNewDataFile();
				break;
			case Commands.CHANGE_DATA_FOLDER:
				changeDataFolder();
				break;
			case Commands.EXIT_APPLICATION:
				exitApplication();
				break;
			case Commands.SWITCH_DATA_FILE:
				switchDataFile(view.getSelectedDataFile());
				break;
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox source = (JCheckBox)e.getSource();
		switch (source.getName()) {
			case "showFullURLsCheckBox":
				if (appData != null) {
					// Functions that need initialized appData
					if (e.getStateChange() == ItemEvent.SELECTED) showFullURLs();
					if (e.getStateChange() == ItemEvent.DESELECTED) showURLs();
				}
				break;
		}
	}
	
	public class WindowAdapterSub extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			exitApplication();
		}
	}
	
	/*
	 * Create an input dialog that takes user input for new data file name.
	 * Create a new named data file and add it to View.dataFilesComboBox
	 * Switch to that new data file.
	 */
	private void createNewDataFile() {
		String fileName = View.createInputDialog("Enter new data file name:");
		
		if (fileName == null) {
			// User clicks cancel, do nothing
		}
		else if (!FileUtils.isFileNameValid(fileName)) {
			View.createMessageDialog("Invalid file name.");
		}
		else if (view.getDataFiles().contains(fileName)) {
			View.createMessageDialog("File name existed.");
		}
		else {
			String filePath = AppData.dataFolder + File.separator + fileName + ".txt";
			try (
				FileWriter fw = new FileWriter(filePath);
			) {
				view.addDataFileItem(fileName);
				view.setSelectedDataFile(fileName); // Creates an ItemEvent
				
				// In case ItemEvent is not created, switch data file manually
				if (appData == null) switchDataFile(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Based on View.showFullURLsCheckBox, save the output URLs in
	 * AppData to the currently selected data file, then clear it to
	 * avoid double saving.
	 */
	private void saveDataFile() {
		String urlsToSave;
		if (view.isShowingURLsFull()) urlsToSave = appData.getOutputURLsFull();
		else urlsToSave = appData.getOutputURLs();
		
		if (urlsToSave == null || urlsToSave.isEmpty()) View.createMessageDialog("Nothing to save.");
		else {
			String filePath = AppData.dataFolder + File.separator + appData.getDataFileName() + ".txt";
			try (
				FileWriter fw = new FileWriter(filePath, true);
			) {
				fw.write(urlsToSave);
				if (view.isShowingURLsFull()) appData.setOutputURLsFull("");
				else appData.setOutputURLs("");
				
				View.createMessageDialog("Data file saved.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Create a file chooser to select a data folder, with the current folder is
	 * loaded by calling getLastDataFolder(). After choosing, save the selection
	 * to AppData and config file.
	 */
	private void changeDataFolder() {
		JFileChooser fileChooser = View.createFolderChooser("Choose data folder");
		fileChooser.setCurrentDirectory(new File(getLastDataFolder()));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File folder = fileChooser.getSelectedFile();
			AppData.dataFolder = folder.getAbsolutePath();
			saveLastDataFolder(AppData.dataFolder);
		}
	}
	
	/*
	 * Create a confirm dialog to ask for saving unsaved AppData.outputURLs
	 * Exit if there is nothing to save, or if user clicks NO, or after saving.
	 */
	private void exitApplication() {
		if (appData == null || appData.getOutputURLs() == null ||
			appData.getOutputURLs().isEmpty()) System.exit(0);
		else {
			showURLs();
			switch (View.createConfirmDialog("Want to save unsaved output URLs?")) {
				case 0: // YES
					saveDataFile();
					System.exit(0);
					break;
				case 1: // NO
					System.exit(0);
					break;
				case 2: // CANCEL
					break;
			}
		}
	}
	
	/*
	 * Check the input URLs set. For each URL, call URLHandler.checkURL()
	 * passing the path-removed URL. If the URL is new, store its 2 forms
	 * to AppData and display the path-removed form.
	 */
	private void checkInputURLs(Set<String> inputURLs) {
		StringBuilder outputURLs = new StringBuilder();
		StringBuilder outputURLsFull = new StringBuilder();
		int outputURLsCount = 0;
		
		if (inputURLs.size() == 0) View.createMessageDialog("Nothing to check.");
		else {
			for (String inputURL : inputURLs) {
				inputURL = inputURL.toLowerCase();
				String urlFull = URLHandler.replaceURLPrefix(inputURL, URLHandler.STD_URL_PREFIX);
				String url = URLHandler.removeURLPath(urlFull);
				
				// Check existence based on the path-removed URL
				boolean urlIsNew = URLHandler.checkURL(url, appData.getDataURLs());
				if (urlIsNew) {
					outputURLs.append(url).append(System.lineSeparator());
					outputURLsFull.append(urlFull).append(System.lineSeparator());
					outputURLsCount++;
				}
			}
			appData.setOutputURLs(outputURLs.toString());
			appData.setOutputURLsFull(outputURLsFull.toString());
			
			view.setInputURLsCount(inputURLs.size());
			view.setOutputURLsCount(outputURLsCount);
			view.setOutputURLs(appData.getOutputURLs());
		}
	}
	
	/*
	 * Create a file chooser dialog to select a text file. For each line
	 * (URL) read from that file, call URLHandler.checkURL() passing the
	 * path-removed URL. If the URL is new, store its 2 forms to AppData
	 * and display the path-removed form.
	 */
	private void checkInputURLsFromFile() {
		JFileChooser fileChooser = View.createFileChooser("Choose file", View.TEXT_FILE_FILTER);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			StringBuilder outputURLs = new StringBuilder();
			StringBuilder outputURLsFull = new StringBuilder();
			int inputURLsCount = 0;
			int outputURLsCount = 0;
			
			try (
				BufferedReader br = new BufferedReader(new FileReader(file));
			) {
				String readURL;
				while ((readURL = br.readLine()) != null && !readURL.replace("\\s+", "").isEmpty()) {
					readURL = readURL.toLowerCase();
					String urlFull = URLHandler.replaceURLPrefix(readURL, URLHandler.STD_URL_PREFIX);
					String url = URLHandler.removeURLPath(urlFull);
					inputURLsCount++;
					
					// Check existence based on the path-removed URL
					boolean urlIsNew = URLHandler.checkURL(url, appData.getDataURLs());
					if (urlIsNew) {
						outputURLs.append(url).append(System.lineSeparator());
						outputURLsFull.append(urlFull).append(System.lineSeparator());
						outputURLsCount++;
					}
				}
				appData.setOutputURLs(outputURLs.toString());
				appData.setOutputURLsFull(outputURLsFull.toString());
				
				view.setInputURLsCount(inputURLsCount);
				view.setOutputURLsCount(outputURLsCount);
				view.setOutputURLs(appData.getOutputURLs());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Create an input dialog that takes user input for keyword to search
	 * on Google. Call checkInputURLs() passing resultURLs got from Google.
	 */
	private void getGoogleURLs() {
		String keyword = View.createInputDialog("Search on Google:");
		
		if (keyword == null || keyword.replaceAll("\\s+", "").isEmpty()) {
			// User clicks cancel or enters empty keyword, do nothing
		}
		else {
			Set<String> inputURLs = new HashSet<>();
			int page = 0;
			while (true) {
				Document resultPage = GoogleSearchHandler.getSearchResultPage(keyword, page++);
				Set<String> resultURLs = GoogleSearchHandler.getResultURLs(resultPage);
				inputURLs.addAll(resultURLs);
				
				// Stop searching when there is no more result
				if (resultURLs.size() == 0) break;
			}
			
			checkInputURLs(inputURLs);
		}
	}
	
	private void switchDataFile(String selectedDataFile) {
		appData = new AppData(selectedDataFile);
		clearAppData();
	}
	
	private void clearAppData() {
		appData.setOutputURLs("");
		appData.setOutputURLsFull("");
		
		view.setInputURLs("");
		view.setInputURLsCount(0);
		view.setOutputURLs("");
		view.setOutputURLsCount(0);
		view.setShowingURLsFull(false);
	}

	private void showFullURLs() {
		view.setOutputURLs(appData.getOutputURLsFull());
	}
	
	private void showURLs() {
		view.setOutputURLs(appData.getOutputURLs());
	}
	
	private void loadDataFiles() {
		File[] fileList = new File(AppData.dataFolder).listFiles();
		if (fileList == null) {
			View.createMessageDialog("Data folder not found. Application will not work.");
			exitApplication();
		}
		
		Arrays.asList(fileList).stream()
			.filter(file -> file.isFile())
			.filter(file -> ".txt".equals(FileUtils.getFileExtension(file.getName())))
			.forEach(file -> view.addDataFileItem(FileUtils.removeFileExtension(file.getName())));
	}
	
	private String getLastDataFolder() {
		String lastDataFolder = AppData.dataFolder;
		try (
			FileReader fr = new FileReader(AppData.CONFIG_FILE_PATH);
		) {
			Properties prop = new Properties();
			prop.load(fr);
			String folder = prop.getProperty("lastDataFolder");
			if (folder != null) lastDataFolder = folder;
		} catch (FileNotFoundException e) {
			saveLastDataFolder("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lastDataFolder;
	}
	
	private void saveLastDataFolder(String folder) {
		try (
			FileWriter fw = new FileWriter(AppData.CONFIG_FILE_PATH);
		) {
			Properties prop = new Properties();
			prop.setProperty("lastDataFolder", folder);
			prop.store(fw, "URL Tool configuration file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}