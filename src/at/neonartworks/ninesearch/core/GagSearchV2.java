package at.neonartworks.ninesearch.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import at.neonartworks.ninesearch.utils.GagSection;
import at.neonartworks.ninesearch.utils.GagUtils;

/**********************************************************************
 * 
 * A very simple and easy to use (unofficial) 9Gag Java api. <b> Please note
 * that we are currently working on a better version! </b>
 * 
 * | Project : 9Gag Search API V2
 *
 * Program name : GagSearchV2.java
 *
 * Author : Florian Wagner aka. neonartworks
 *
 * Date created : 06.06.2017
 *
 * Purpose : Small and basic 9gag search api.
 * 
 *
 * Revision History : |
 **********************************************************************/

public class GagSearchV2 {

	private List<GagPost> posts = new ArrayList<GagPost>();
	private URL url;
	private InputStream is = null;
	private BufferedReader br;
	private String line;
	private StringBuilder sb = new StringBuilder();

	public GagSearchV2() {
	}

	/**
	 * This method initializes the GagSearch query. It has to be called before
	 * the GagSearchV2.search() method! If this method is not called
	 * GagSearchV2.search() will throw an error!
	 * 
	 * @param query
	 *            This is your search query.
	 * @throws IOException
	 */
	public void initSearch(String query) throws IOException {
		query = query.replaceAll(" ", "+");
		url = new URL(GagUtils.getSearchLink() + query);

		GagUtils.initSearch(url, is, br, line, sb);

	}

	/**
	 * Has to be called after GagSearchV2.initSearch()! This method returns a
	 * List with GagPosts. The size of the returned List is always 10. This
	 * means only the first 10 results from 9Gag will be returned!
	 * 
	 * @return a List<GagPost> containing the first 10 search results from 9Gag.
	 */
	public List<GagPost> search() {
		List<String> rawList = GagUtils.filter(sb.toString());
		posts = GagUtils.createPostEntries(rawList);
		return posts;
	}

	/**
	 * This method does not have to be called after initSearch() -> it works on
	 * its own! It returns the first 10 9Gag entries from the section, given
	 * with 's', as a List<GagPost>. For every Section, except for
	 * GagSection.FRESH and GagSection.TRENDING, the posts will be taken out of
	 * 9Gag-GagSection-HOT.
	 * 
	 * 
	 * @param s
	 *            the desired section you want the first 10 results from 9Gag.
	 * @return a List<GagPost> with the first 10 search results from 9Gag.
	 * @throws IOException
	 */
	public List<GagPost> getFromSection(GagSection s) throws IOException {

		url = new URL(GagUtils.GetSectionLink() + GagUtils.refactorSection(s));
		GagUtils.initSearch(url, is, br, line, sb);

		List<String> rawList = GagUtils.filterSection(sb.toString());
		posts = GagUtils.createPostFromSectionEntries(rawList);
		return posts;

	}

}