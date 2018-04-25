package com.vieejtddwcs.urltool;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearchHandler {

	private GoogleSearchHandler() {
		throw new AssertionError();
	}
	
	/**
	 * Get a Google Search result page Document object with the
	 * specified keyword and page.
	 * <p>
	 * This method will specifies 100 results per page.
	 * 
	 * @param keyword - the search keyword
	 * @param page - the page number of the result page
	 * @return a HTML Document object, null if I/O errors occurs
	 * @throws NullPointerException if <b>keyword</b> is null
	 */
	public static Document getSearchResultPage(String keyword, int page) {
		Objects.requireNonNull(keyword);
		
		Document resultPage = null;
		try {
			resultPage = Jsoup.connect("https://www.google.com/search")
					.data("q", URLEncoder.encode(keyword, "UTF-8"))
					.data("num", "100")
					.data("start", 100*page + "")
					.get();
		} catch (HttpStatusException e) {
			View.createMessageDialog("HTTP error: status code " + e.getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultPage;
	}
	
	/**
	 * Get a set of Google Search result URLs from the resultPage
	 * object.
	 * <p>
	 * The result URL is the <i>href</i> attribute of the <i>a</i>
	 * element inside the <i>h3</i> element which has attribute
	 * class <i>r</i>.
	 * 
	 * @param resultPage - the search result page
	 * @return a Set containing the result URLs found
	 * @throws NullPointerException if <b>resultPage</b> is null
	 */
	public static Set<String> getResultURLs(Document resultPage) {
		Objects.requireNonNull(resultPage);
		
		Set<String> resultURLs = new HashSet<>();
		Elements resultElements = resultPage.getElementsByClass("r");
		for (Element r : resultElements) {
			Element a = r.getElementsByTag("a").first();
			resultURLs.add(a.attr("href"));
		}
		return resultURLs;
	}
	
}