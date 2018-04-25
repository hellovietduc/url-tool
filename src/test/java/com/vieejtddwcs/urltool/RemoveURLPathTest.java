package com.vieejtddwcs.urltool;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveURLPathTest {

	@Test
	public void testNoRemoveURLPath1() {
		String url = URLHandler.removeURLPath("http://example.com");
		assertEquals("http://example.com", url);
	}
	
	@Test
	public void testNoRemoveURLPath2() {
		String url = URLHandler.removeURLPath("http://example.com/");
		assertEquals("http://example.com/", url);
	}
	
	// 1-level path, only word
	@Test
	public void teNoRemoveURLPath1() {
		String url = URLHandler.removeURLPath("http://example.com/movie");
		assertEquals("http://example.com", url);
	}
	
	// 2-level path, only word
	@Test
	public void teNoRemoveURLPath2() {
		String url = URLHandler.removeURLPath("http://example.com/movie/logan");
		assertEquals("http://example.com", url);
	}
	
	// Path includes number
	@Test
	public void teNoRemoveURLPath3() {
		String url = URLHandler.removeURLPath("http://example.com/movie1");
		assertEquals("http://example.com", url);
	}
	
	// Path includes special character
	@Test
	public void teNoRemoveURLPath4() {
		String url = URLHandler.removeURLPath("http://example.com/m*vie");
		assertEquals("http://example.com", url);
	}
	
	// Path includes double-slash
	@Test
	public void teNoRemoveURLPath5() {
		String url = URLHandler.removeURLPath("http://example.com/movie//");
		assertEquals("http://example.com", url);
	}
	
	// Path includes filename extension
	@Test
	public void teNoRemoveURLPath6() {
		String url = URLHandler.removeURLPath("http://example.com/movie.png");
		assertEquals("http://example.com", url);
	}
	
}