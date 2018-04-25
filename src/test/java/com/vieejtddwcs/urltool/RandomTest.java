package com.vieejtddwcs.urltool;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomTest {

	@Test
	public void testStartsWith() {
		boolean result = "".startsWith("www.");
		assertFalse(result);
	}
	
	@Test
	public void testStartsWithEmpty() {
		boolean result = "".startsWith("");
		assertTrue(result);
	}
	
	@Test
	public void testReplace() {
		String result = "".replaceFirst("www.", "http://");
		assertEquals("", result);
	}
	
	@Test
	public void testSubstring() {
		String sub = "hello".substring(1, 2);
		assertEquals("e", sub);
	}
	
}