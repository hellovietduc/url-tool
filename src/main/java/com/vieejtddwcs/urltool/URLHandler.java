package com.vieejtddwcs.urltool;

import java.util.Objects;
import java.util.Set;

public class URLHandler {
	
	public static final String STD_URL_PREFIX = "http://";
	private static final String[] URL_PREFIXES = {"https://www.",
												"http://www.",
												"https://",
												"www."};

	private URLHandler() {
		throw new AssertionError();
	}
	
	/**
	 * Replace the prefix of this URL with the specified prefix. If the URL's prefix
	 * doesn't match any application's pre-defined prefixes, add the specified prefix
	 * to the start of this URL.
	 * <p>
	 * For example: <b>url</b> is "https://www.example.com", <b>prefix</b> is "http://"
	 * then the method will return "http://example.com".
	 * 
	 * @param url - the source URL
	 * @param prefix - the given prefix
	 * @return a URL with the new prefix, or the source URL if it already has the given
	 * prefix
	 * @throws NullPointerException if <b>url</b> or <b>prefix</b> is null
	 */
	public static String replaceURLPrefix(String url, String prefix) {
		Objects.requireNonNull(url);
		Objects.requireNonNull(prefix);
		
		for (String p : URL_PREFIXES) {
			if (url.startsWith(p)) return url.replaceFirst(p, prefix);
		}
		if (url.startsWith(prefix)) return url;
		return prefix + url;
	}
	
	/**
	 * Remove the path of this URL.
	 * <p>
	 * For example: <b>url</b> is "http://example.com/movie/logan",
	 * the method will return "http://example.com"
	 * 
	 * @param url - the source URL
	 * @return a URL with the path removed
	 * @throws NullPointerException if <b>url</b> is null
	 * @throws IllegalArgumentException if <b>url</b>'s length is
	 * smaller or equal to standard URL prefix's length
	 */
	public static String removeURLPath(String url) {
		Objects.requireNonNull(url);
		if (url.length() <= STD_URL_PREFIX.length())
			throw new IllegalArgumentException("Input URL's length must be "
					+ "greater than standard URL prefix's length.");
		
		return STD_URL_PREFIX +
				url.substring(STD_URL_PREFIX.length(), url.length())
				.replaceAll("\\/.+$", "");
	}
	
	/**
	 * Check if this URL is new to the data source. If yes, add it to the data
	 * source.
	 * 
	 * @param url - the given URL
	 * @param dataSource - the data source
	 * @return true if this URL is new and added to the data source, otherwise
	 * return false
	 * @throws NullPointerException if <b>url</b> or <b>dataSource</b> is null
	 * or empty
	 */
	public static boolean checkURL(String url, Set<String> dataSource) {
		Objects.requireNonNull(url);
		Objects.requireNonNull(dataSource);
		return dataSource.add(url);
	}
	
}