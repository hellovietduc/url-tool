package com.vieejtddwcs.urltool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class GetFileExtensionTest {

	@Test
	public void testGetNormalFileExtension() {
		String ext = FileUtils.getFileExtension("file.txt");
		assertEquals(".txt", ext);
	}
	
	@Test
	public void testGetNullFileExtension() {
		String ext = FileUtils.getFileExtension(null);
		assertNull(ext);
	}
	
	// Filename has no extension
	@Test
	public void testGetNoFileExtension() {
		String ext = FileUtils.getFileExtension("file");
		assertNull(ext);
	}

	// The filename part has an extension
	@Test
	public void testGetConfusingFileExtension() {
		String ext = FileUtils.getFileExtension("file.txt.doc");
		assertEquals(".doc", ext);
	}
	
}