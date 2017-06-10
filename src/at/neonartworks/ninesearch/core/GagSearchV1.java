package at.neonartworks.ninesearch.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.neonartworks.ninesearch.utils.GagSection;
import at.neonartworks.ninesearch.utils.GagUtils;

/**********************************************************************
 * | Project : 9Gag Search API
 *
 * Program name : GSearch.java
 *
 * Author : Florian Wagner aka. neonartworks
 *
 * Date created : 05.06.2017
 *
 * Purpose : Small and basic 9gag search api.
 *
 * Revision History : |
 **********************************************************************/

@Deprecated
public class GagSearchV1 {


	private URL url; // url used for fetching
	private InputStream is = null; // fetching is done with the inputstream
	private BufferedReader br;
	private String line;
	private String filter = "data-entry-id=";

	private List<String> sList = new ArrayList<String>(); // main list where the
															// links/ids are
															// stored

	private List<String> urlList = new ArrayList<String>(); // list which
															// contains all
															// links fetched

	private Random random = new Random();

	private StringBuilder sb = new StringBuilder();

	public GagSearchV1() {

	}

	/**
	 * Returns a random post from the chosen section s.
	 * 
	 * @param s
	 *            The section you want a post from.
	 * @param hot_fresh
	 *            1 is equal to hot, 0 is equal to fresh
	 * @return URl of the post as String
	 * @throws IOException
	 */
	public String getPostFromSection(GagSection s, boolean hot_fresh) throws IOException {

		String section = GagUtils.refactorSection(s);

		if (hot_fresh && (!section.equals("trending")) && (!section.equals("fresh")) && !section.equals("hot"))
			url = new URL(GagUtils.GetSectionLink() + section + "/hot/");

		else if (!hot_fresh && (!section.equals("trending")) && (!section.equals("fresh")) && !section.equals("hot"))
			url = new URL(GagUtils.GetSectionLink() + section + "/fresh/");
		else
			url = new URL(GagUtils.GetSectionLink() + section);

		// System.out.println("Getting post from: " + url.toString()); //Debug
		// Message

		is = url.openStream(); // throws an IOException
		br = new BufferedReader(new InputStreamReader(is));

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return filterSearch(sb.toString(), filter);
	}

	/**
	 * /** Search on 9gag like with the search function.
	 * 
	 * @param query
	 *            The search entry
	 * @return A List containing all results found
	 * @throws IOException
	 */

	public List<String> searchAll(String query) throws IOException {

		query = query.replaceAll(" ", "+");

		url = new URL(GagUtils.getSearchLink() + query);
		is = url.openStream(); // throws an IOException
		br = new BufferedReader(new InputStreamReader(is));

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return filterSearchList(sb.toString(), filter);
	}

	/**
	 * Search on 9gag like with the search function. An URL is returned as
	 * string which is one of the results (random)
	 * 
	 * @param query
	 *            The search entry
	 * @return a url of one of the results
	 * @throws IOException
	 */

	public String search(String query) throws IOException {

		query = query.replaceAll(" ", "+");

		url = new URL(GagUtils.getSearchLink() + query);
		is = url.openStream(); // throws an IOException
		br = new BufferedReader(new InputStreamReader(is));

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return filterSearch(sb.toString(), filter);
	}

	private String filterSearch(String content, String filter) {
		String[] s = content.split(filter);
		for (String b : s) {
			b = b.split(" ")[0];
			b.trim();
			sList.add(b);
		}
		sList = GagUtils.sortDuplicates(sList);
		return createURL();

	}

	private List<String> filterSearchList(String content, String filter) {
		String[] s = content.split(filter);
		for (String b : s) {

			b = b.split(" ")[0];
			sList.add(b);

		}
		sList = GagUtils.sortDuplicates(sList);
		return createURLs();
	}

	// ---------------------------
	// public String returnDebug() {
	// return sList.toString();
	// } DEBUG METHOD
	// ---------------------------

	private String createURL() {
		int index = random.nextInt(sList.size());
		String image = sList.get(index);
		image = image.replaceAll("\"", "");
		image.trim();
		return GagUtils.getPhotoLink() + image + GagUtils.getPhotoTail();
	}

	private List<String> createURLs() {
		for (int i = 0; i < sList.size(); i++) {
			String image = sList.get(i);
			image = image.replaceAll("\"", "");
			image.trim();
			urlList.add(GagUtils.getPhotoLink() + image + GagUtils.getPhotoTail());
		}
		return urlList;
	}
}