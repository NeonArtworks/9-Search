package at.neonartworks.ninesearch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.neonartworks.ninesearch.core.GagPost;

/**********************************************************************
 * | Project : 9Gag Search API
 *
 * Program name : GagUtils.java
 *
 * Author : Florian Wagner aka. neonartworks
 *
 * Date created : 06.06.2017
 *
 * Purpose : Helper class for the api. It contains a few useful methods.
 *
 * Revision History : |
 **********************************************************************/

public class GagUtils {

	private static String mLink = "https://9gag.com/search?query=";
	private static String pLink = "https://images-cdn.9gag.com/photo/";
	private static String pTail = "_700b.jpg";
	private static String mSection = "https://9gag.com/";
	private static String gLink = "https://img-9gag-fun.9cache.com/photo/";
	private static String gTail = "_220x145.jpg";
	private static String pFilter = "<article";
	private static String sFilter = "<ul class=\"overview-list badge-entry-collection\">";
	private static String sFilter2 = "<div class=\"loading\">";
	private static String secFilter = "<div class=\"badge-entry-collection\">";

	private static boolean debug = false;

	/**
	 * Section names are not the same as the display names of 9gag. This
	 * function returns the real name used by 9gag and not the alias.
	 * 
	 * @param s
	 *            the section
	 * @return the real name as string
	 */
	public static String refactorSection(GagSection s) {

		String sec = s.toString().toLowerCase();

		sec = sec.replace("_", "");

		if (sec.equals("marveldc"))
			sec = "superhero";
		else if (sec.equals("movietv"))
			sec = "movie-tv";
		else if (sec.equals("nfnf"))
			sec = "horror";
		else if (sec.equals("studying"))
			sec = "school";
		else if (sec.equals("cuteanimals"))
			sec = "cute";
		else if (sec.equals("gamersunit"))
			sec = "gaming";
		else if (sec.equals("girlythings"))
			sec = "girly";
		else if (sec.equals("madlab"))
			sec = "science";
		else if (sec.equals("damnyoupolitics"))
			sec = "politics";
		else if (sec.equals("animemanga"))
			sec = "anime-manga";
		else if (sec.equals("yummy"))
			sec = "food";
		else if (sec.equals("jcena"))
			sec = "cena";

		return sec;
	}

	/**
	 * Creates a HashSet of the list and then gets casted back to a List A
	 * HashSet does not have duplicate values, this makes it easier to delete
	 * duplicate entries
	 * 
	 * @param list
	 *            the same list without duplicates.
	 */

	public static List<String> sortDuplicates(List<String> list) {
		Set<String> set = new HashSet<String>(list);
		list = new ArrayList<String>(set);
		list.remove("<!DOCTYPE");
		return list;
	}

	/**
	 * Returns the search/query link of 9gag.
	 * 
	 * @return search link
	 */
	public static String getSearchLink() {
		return mLink;
	}

	/**
	 * Returns the link from the main photo host server.
	 * 
	 * @return photo host server link
	 */
	public static String getPhotoLink() {
		return pLink;
	}

	/**
	 * Every image has the same suffix. It is returned by this function.
	 * 
	 * @return photo suffix
	 */
	public static String getPhotoTail() {
		return pTail;
	}

	/**
	 * Gets the main 9gag link. This is used for section fetching.
	 * 
	 * @return main 9gag link
	 */
	public static String GetSectionLink() {
		return mSection;
	}

	/**
	 * Gets the thumbnail link of the photo.
	 * 
	 * @return thumbnail link
	 */
	public static String GetThumbLink() {
		return gLink;
	}

	/**
	 * Every image (thumbnail) has the same suffix. It is returned by this
	 * function.
	 * 
	 * @return thumbnail suffix
	 */
	public static String GetThumbTail() {
		return gTail;
	}

	/**
	 * Returns the filter used for searching.
	 * 
	 * @return search filter
	 */
	@SuppressWarnings("unused")
	private static String getFilter() {
		return pFilter;
	}

	/**
	 * Returns the search filter used in this api.
	 * 
	 * @return search filter
	 */
	private static String getSearchFilter() {
		return sFilter;
	}

	/**
	 * Returns the second search filter used in this api.
	 * 
	 * @return search filter 2
	 */
	private static String getSearchFilter2() {
		return sFilter2;
	}

	/**
	 * Return the section filter used in this api.
	 * 
	 * @return section filter 1
	 */
	private static String getSecFilter() {
		return secFilter;
	}

	/**
	 * Filters the search and returns a raw list with entries.
	 * 
	 * @param content
	 *            the content to filter
	 * @return rawList of entry values
	 */

	public static List<String> filter(String content) {
		content = content.split(getSearchFilter())[1];
		content = content.split(getSearchFilter2())[0];
		List<String> rawList = new ArrayList<String>();
		for (String b : content.split("<li")) {
			if (debug)
				System.out.println(b);
			b.trim();
			rawList.add(b);
		}
		return rawList;
	}

	public static List<String> filterSection(String content) {
		content = content.split(getSecFilter())[1];
		content = content.split(getSearchFilter2())[0];
		List<String> rawList = new ArrayList<String>();
		for (String b : content.split("<article")) {
			if (debug)
				System.out.println(b);
			b.trim();
			rawList.add(b);
		}
		return rawList;
	}

	public static void initSearch(URL url, InputStream is, BufferedReader br, String line, StringBuilder sb)
			throws IOException {
		is = url.openStream(); // throws an IOException
		br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
	}

	/**
	 * Creates the final GagPost-list from the raw values given by the "filter"
	 * function.
	 * 
	 * @param rawList
	 *            the raw list with the values
	 * @return
	 */
	public static List<GagPost> createPostFromSectionEntries(List<String> rawList) {
		String id = "";//
		String thumbnail = "";//
		String image = "";//
		String title = "";//
		String link = ""; //
		String comments = "";
		String votes = "";
		List<GagPost> postList = new ArrayList<GagPost>();
		for (String entrie : rawList) {

			if (!entrie.equals("") || (!entrie.equals(" "))) {
				if (entrie.contains("Click to view this post."))
					entrie = entrie.replaceAll("<p>Click to view this post\\.</p>", " ");
				id = entrie.split("data-entry-url=")[0];
				id = id.trim();
				id = id.replace("data-entry-id=", "");
				id = id.replace("\"", "");

				thumbnail = GetThumbLink() + id + GetThumbTail();
				image = getPhotoLink() + id + getPhotoTail();
				link = GetSectionLink() + "gag/" + id;

				title = entrie;
				String tmp = title.trim().split("alt=")[0];
				title = title.replace(tmp, "");
				title = title.split("/>")[0];
				title = title.replace("alt=", "");
				title = title.replace("\"", "");
				title = title.split("style")[0];
				title = title.split("></div>")[0];
				title = title.trim();

				votes = entrie;

				votes = votes.split("data-entry-comments=")[0];
				tmp = votes.split("data-entry-votes=")[0];
				votes = votes.replaceAll(tmp, "");
				votes = votes.replace("data-entry-votes=", "");
				votes = votes.replace("\"", "");
				votes = votes.trim();
				// votes = votes.replace(",", "");

				comments = entrie;

				comments = comments.split("id=\"jsid-entry-entity-")[0];
				tmp = comments.split("data-entry-comments=")[0];
				comments = comments.replaceAll(tmp, "");
				comments = comments.replaceAll("data-entry-comments=", "");
				comments = comments.replace("\"", "");
				comments = comments.trim();
				// System.out.println(comments);

				if (debug) {
					System.out.println(entrie);
					System.out.println(id);
					System.out.println(thumbnail);
					System.out.println(image);
					System.out.println(link);
					System.out.println(title);
					System.out.println(votes);
				}
				if ((!id.equals("")))

					postList.add(new GagPost(id, thumbnail, image, title, link, Integer.valueOf(comments),
							Integer.valueOf(votes)));

			}
		}

		return postList;
	}

	/**
	 * Creates the final GagPost-list from the raw values given by the "filter"
	 * function.
	 * 
	 * @param rawList
	 *            the raw list with the values
	 * @return
	 */
	public static List<GagPost> createPostEntries(List<String> rawList) {
		String id = "";//
		String thumbnail = "";//
		String image = "";//
		String title = "";//
		String link = ""; //
		@SuppressWarnings("unused")
		String comments = "";
		String votes = "";
		List<GagPost> postList = new ArrayList<GagPost>();
		for (String entrie : rawList) {

			if (!entrie.equals("") || (!entrie.equals(" "))) {
				if (entrie.contains("Click to view this post."))
					entrie = entrie.replaceAll("<p>Click to view this post.</p>", " ");
				id = entrie.split("data-entry-url=")[0];
				id = id.trim();
				id = id.replace("data-entry-id=", "");
				id = id.replace("\"", "");

				thumbnail = GetThumbLink() + id + GetThumbTail();
				image = getPhotoLink() + id + getPhotoTail();
				link = GetSectionLink() + "gag/" + id;

				title = entrie;
				String tmp = title.trim().split("alt=")[0];
				title = title.replace(tmp, "");
				title = title.split("/>")[0];
				title = title.replace("alt=", "");
				title = title.replace("\"", "");
				title = title.trim();

				votes = entrie;

				votes = votes.split("</p>")[0];
				tmp = votes.trim().split("<p>")[0];
				votes = votes.replace(tmp, "");
				votes = votes.trim();
				votes = votes.replace("<p>", "");
				votes = votes.replace(" ", "");
				votes = votes.replace("points", "");
				votes = votes.replace(",", "");

				if (debug) {
					System.out.println(entrie);
					System.out.println(id);
					System.out.println(thumbnail);
					System.out.println(image);
					System.out.println(link);
					System.out.println(title);
					System.out.println(votes);
				}
				if ((!id.equals("")))

					postList.add(new GagPost(id, thumbnail, image, title, link, 0, Integer.valueOf(votes)));

			}
		}

		return postList;
	}
}