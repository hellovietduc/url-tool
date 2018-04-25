package com.vieejtddwcs.urltool;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	public static final FileNameExtensionFilter TEXT_FILE_FILTER =
			new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
	
	private final Controller controller;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu toolMenu;
	private JMenuItem newDataFileMenuItem;
	private JMenuItem saveDataFileMenuItem;
	private JMenuItem dataFolderMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem checkInputMenuItem;
	private JMenuItem checkFromFileMenuItem;
	private JMenuItem getGoogleURLsMenuItem;
	
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JComboBox<String> dataFilesComboBox;
	private JCheckBox showFullURLsCheckBox;
	
	private JScrollPane inputScrollPane;
	private JScrollPane outputScrollPane;
	private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	
	private JButton checkButton;
	private JButton saveButton;
	private JButton clearButton;
	private JButton fileButton;
	private JButton googleButton;
	
	private GroupLayout layout;
	
	public View() {
		super();
		setSize(950, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		initComponents();
		addComponentsToParents();
		layoutComponents();
		setVisible(true);
		
		controller = new Controller(this);
		addListener();
	}
	
	private void initFileMenu() {
		fileMenu = new JMenu("File");
		newDataFileMenuItem = new JMenuItem("New Data File");
		saveDataFileMenuItem = new JMenuItem("Save Data File");
		dataFolderMenuItem = new JMenuItem("Change Data Folder");
		exitMenuItem = new JMenuItem("Exit");
		
		fileMenu.setMnemonic('F');
		newDataFileMenuItem.setMnemonic('N');
		saveDataFileMenuItem.setMnemonic('S');
		dataFolderMenuItem.setMnemonic('D');
		exitMenuItem.setMnemonic('x');
		
		newDataFileMenuItem.setActionCommand(Commands.CREATE_NEW_DATA_FILE);
		saveDataFileMenuItem.setActionCommand(Commands.SAVE_DATA_FILE);
		dataFolderMenuItem.setActionCommand(Commands.CHANGE_DATA_FOLDER);
		exitMenuItem.setActionCommand(Commands.EXIT_APPLICATION);
	}
	
	private void initToolMenu() {
		toolMenu = new JMenu("Tool");
		checkInputMenuItem = new JMenuItem("Check Input URLs");
		checkFromFileMenuItem = new JMenuItem("Check Input URLs From File");
		getGoogleURLsMenuItem = new JMenuItem("Get Google URLs");
		
		toolMenu.setMnemonic('T');
		checkInputMenuItem.setMnemonic('C');
		checkFromFileMenuItem.setMnemonic('k');
		getGoogleURLsMenuItem.setMnemonic('G');
		
		checkInputMenuItem.setActionCommand(Commands.CHECK_INPUT_URLS);
		checkFromFileMenuItem.setActionCommand(Commands.CHECK_INPUT_FROM_FILE);
		getGoogleURLsMenuItem.setActionCommand(Commands.GET_GOOGLE_URLS);
	}
	
	private void initInputPane() {
		inputLabel = new JLabel("Input URLs: 0");
		dataFilesComboBox = new JComboBox<String>();
		inputScrollPane = new JScrollPane();
		inputTextArea = new JTextArea();
		
		dataFilesComboBox.setActionCommand(Commands.SWITCH_DATA_FILE);
	}
	
	private void initOutputPane() {
		outputLabel = new JLabel("Output URLs: 0");
		showFullURLsCheckBox = new JCheckBox("Show full URLs");
		outputScrollPane = new JScrollPane();
		outputTextArea = new JTextArea();
		
		showFullURLsCheckBox.setActionCommand(Commands.SHOW_FULL_URLS);
		showFullURLsCheckBox.setName("showFullURLsCheckBox");
	}
	
	private void initButtons() {
		checkButton = new JButton("Check");
		saveButton = new JButton("Save");
		clearButton = new JButton("Clear");
		fileButton = new JButton("File");
		googleButton = new JButton("Google");
		
		checkButton.setActionCommand(Commands.CHECK_INPUT_URLS);
		saveButton.setActionCommand(Commands.SAVE_DATA_FILE);
		clearButton.setActionCommand(Commands.CLEAR_APP_DATA);
		fileButton.setActionCommand(Commands.CHECK_INPUT_FROM_FILE);
		googleButton.setActionCommand(Commands.GET_GOOGLE_URLS);
	}
	
	private void initComponents() {
		menuBar = new JMenuBar();
		initFileMenu();
		initToolMenu();
		initInputPane();
		initOutputPane();
		initButtons();
	}
	
	private void addComponentsToParents() {
		fileMenu.add(newDataFileMenuItem);
		fileMenu.add(saveDataFileMenuItem);
		fileMenu.add(dataFolderMenuItem);
		fileMenu.add(exitMenuItem);
		
		toolMenu.add(checkInputMenuItem);
		toolMenu.add(checkFromFileMenuItem);
		toolMenu.add(getGoogleURLsMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(toolMenu);
		
		setJMenuBar(menuBar);
		
		inputScrollPane.setViewportView(inputTextArea);
		outputScrollPane.setViewportView(outputTextArea);
	}
	
	private void layoutComponents() {
		layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(layout.createSequentialGroup()
							.addComponent(inputLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(dataFilesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(inputScrollPane, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(googleButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addComponent(checkButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
							.addComponent(outputScrollPane, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
							.addGap(7))
						.addGroup(layout.createSequentialGroup()
							.addComponent(outputLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(showFullURLsCheckBox)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(14)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(showFullURLsCheckBox)
								.addComponent(inputLabel)
								.addComponent(dataFilesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(outputLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(inputScrollPane, GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
								.addComponent(outputScrollPane, GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)))
						.addGroup(layout.createSequentialGroup()
							.addGap(203)
							.addComponent(checkButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(saveButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clearButton)
							.addGap(35)
							.addComponent(fileButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(googleButton)))
					.addContainerGap())
		);
		
		getContentPane().setLayout(layout);
	}
	
	private void addListener() {
		newDataFileMenuItem.addActionListener(controller);
		saveDataFileMenuItem.addActionListener(controller);
		dataFolderMenuItem.addActionListener(controller);
		exitMenuItem.addActionListener(controller);
		
		checkInputMenuItem.addActionListener(controller);
		checkFromFileMenuItem.addActionListener(controller);
		getGoogleURLsMenuItem.addActionListener(controller);
		
		dataFilesComboBox.addActionListener(controller);
		showFullURLsCheckBox.addItemListener(controller);
		
		checkButton.addActionListener(controller);
		saveButton.addActionListener(controller);
		clearButton.addActionListener(controller);
		fileButton.addActionListener(controller);
		googleButton.addActionListener(controller);
		
		addWindowListener(controller.new WindowAdapterSub());
	}
	
	public void setInputURLsCount(int count) {
		inputLabel.setText("Input URLs: " + count);
	}
	
	public void setOutputURLsCount(int count) {
		outputLabel.setText("Output URLs: "+ count);
	}
	
	public String getSelectedDataFile() {
		return (String)dataFilesComboBox.getSelectedItem();
	}
	
	/*
	 * This method will create an ItemEvent that
	 * triggers Controller.switchDataFile()
	 * No need to call that method afterwards.
	 */
	public void setSelectedDataFile(String dataFile) {
		dataFilesComboBox.setSelectedItem(dataFile);
	}
	
	public void addDataFileItem(String fileName) {
		dataFilesComboBox.addItem(fileName);
	}
	
	public Set<String> getDataFiles() {
		Set<String> dataFiles = new TreeSet<>();
		for (int i = 0; i < dataFilesComboBox.getItemCount(); i++) {
			dataFiles.add(dataFilesComboBox.getItemAt(i));
		}
		return dataFiles;
	}
	
	public boolean isShowingURLsFull() {
		return showFullURLsCheckBox.isSelected();
	}
	
	public void setShowingURLsFull(boolean show) {
		showFullURLsCheckBox.setSelected(show);
	}
	
	public Set<String> getInputURLs() {
		String[] urls = inputTextArea.getText().split("\n+|\r\n+");
		return Arrays.asList(urls).stream()
				.map(url -> url = url.replaceAll("\\s+", "").toLowerCase())
				.filter(url -> !url.isEmpty())
				.collect(Collectors.toSet());
	}
	
	public void setInputURLs(String input) {
		inputTextArea.setText(input);
	}
	
	public String getOutputURLs() {
		StringBuilder output = new StringBuilder();
		String[] urls = outputTextArea.getText().split("\n+|\r\n+");
		Arrays.asList(urls).stream()
				.map(url -> url = url.replaceAll("\\s+", "").toLowerCase())
				.filter(url -> !url.isEmpty())
				.forEach(url -> output.append(url).append(System.lineSeparator()));
		return output.toString();
	}
	
	public void setOutputURLs(String output) {
		outputTextArea.setText(output);
	}
	
	public static void createMessageDialog(String msg) {
		JOptionPane.showMessageDialog(null,
				msg,
			 	"URL Tool",
			 	JOptionPane.WARNING_MESSAGE);
	}
	
	public static int createConfirmDialog(String msg) {
		return JOptionPane.showConfirmDialog(null,
				msg,
				"URL Tool",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static String createInputDialog(String msg) {
		return JOptionPane.showInputDialog(null,
				msg,
				"URL Tool",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public static JFileChooser createFileChooser(String title, FileNameExtensionFilter filter) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setFileFilter(filter);
		return fileChooser;
	}
	
	public static JFileChooser createFolderChooser(String title) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		return fileChooser;
	}
	
}