package com.vieejtddwcs.urltool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RemoveFileExtensionTest {

	@Test
	public void testRemoveNormalFileExtension() {
		String name = FileUtils.removeFileExtension("data.txt");
		assertEquals("data", name);
	}
	
	@Test
	public void testRemoveNullFileExtension() {
		String name = FileUtils.removeFileExtension(null);
		assertNull(name);
	}
	
	@Test
	public void testRemoveNoFileExtension() {
		String name = FileUtils.removeFileExtension("data");
		assertEquals("data", name);
	}
	
	@Test
	public void testRemoveConfusingFileExtension() {
		String name = FileUtils.removeFileExtension("data.txt.doc");
		assertEquals("data.txt", name);
	}
	
}