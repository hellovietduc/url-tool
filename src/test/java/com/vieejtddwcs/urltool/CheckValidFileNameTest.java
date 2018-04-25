package com.vieejtddwcs.urltool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CheckValidFileNameTest {

	@Test
	public void testValidFileName() {
		boolean result = FileUtils.isFileNameValid("data");
		assertTrue(result);
	}
	
	@Test
	public void testNullFileName() {
		boolean result = FileUtils.isFileNameValid(null);
		assertFalse(result);
	}
	
	@Test
	public void testEmptyFileName() {
		boolean result = FileUtils.isFileNameValid("  ");
		assertFalse(result);
	}
	
	// Invalid character: \
	@Test
	public void testInvalidFileName1() {
		boolean result = FileUtils.isFileNameValid("data\\");
		assertFalse(result);
	}
	
	// Invalid character: /
	@Test
	public void testInvalidFileName2() {
		boolean result = FileUtils.isFileNameValid("/data");
		assertFalse(result);
	}
	
	// Invalid character: :
	@Test
	public void testInvalidFileName3() {
		boolean result = FileUtils.isFileNameValid("da:ta");
		assertFalse(result);
	}
	
	// Invalid character: *
	@Test
	public void testInvalidFileName4() {
		boolean result = FileUtils.isFileNameValid("*data*");
		assertFalse(result);
	}
	
	// Invalid character: ?
	@Test
	public void testInvalidFileName5() {
		boolean result = FileUtils.isFileNameValid("?da?ta?");
		assertFalse(result);
	}
	
	// Invalid character: "
	@Test
	public void testInvalidFileName6() {
		boolean result = FileUtils.isFileNameValid("d\"at\"a");
		assertFalse(result);
	}
	
	// Invalid character: <
	@Test
	public void testInvalidFileName7() {
		boolean result = FileUtils.isFileNameValid("d<ata<");
		assertFalse(result);
	}
	
	// Invalid character: >
	@Test
	public void testInvalidFileName8() {
		boolean result = FileUtils.isFileNameValid(">dat>a");
		assertFalse(result);
	}
	
	// Invalid character: |
	@Test
	public void testInvalidFileName9() {
		boolean result = FileUtils.isFileNameValid("|d|at|a|");
		assertFalse(result);
	}
	
	// Multiple invalid characters
	@Test
	public void testInvalidFileName10() {
		boolean result = FileUtils.isFileNameValid("d*a?t>a");
		assertFalse(result);
	}
	
}