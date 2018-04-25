package com.vieejtddwcs.urltool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Application {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				new View();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
	}
	
}