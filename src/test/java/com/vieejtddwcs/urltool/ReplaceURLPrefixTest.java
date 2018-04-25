package com.vieejtddwcs.urltool;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReplaceURLPrefixTest {

	private static final String PREFIX = "http://";

	// Prefix: https://www.
	@Test
	public void testReplaceURLPrefix1() {
		String url = URLHandler.replaceURLPrefix("https://www.example.com", PREFIX);
		assertEquals(PREFIX + "example.com", url);
	}
	
	// Prefix: http://www.
	@Test
	public void testReplaceURLPrefix2() {
		String url = URLHandler.replaceURLPrefix("http://www.example.com", PREFIX);
		assertEquals(PREFIX + "example.com", url);
	}
	
	// Prefix: https://
	@Test
	public void testReplaceURLPrefix3() {
		String url = URLHandler.replaceURLPrefix("https://example.com", PREFIX);
		assertEquals(PREFIX + "example.com", url);
	}
	
	// Prefix: www.
	@Test
	public void testReplaceURLPrefix4() {
		String url = URLHandler.replaceURLPrefix("www.example.com", PREFIX);
		assertEquals(PREFIX + "example.com", url);
	}
	
	// Non-match prefix: ftp://
	@Test
	public void testReplaceNonMatchURLPrefix() {
		String url = URLHandler.replaceURLPrefix("ftp://example.com", PREFIX);
		assertEquals(PREFIX + "ftp://example.com", url);
	}
	
	// No prefix
	@Test
	public void testReplaceNoURLPrefix() {
		String url = URLHandler.replaceURLPrefix("example.com", PREFIX);
		assertEquals(PREFIX + "example.com", url);
	}
	
}