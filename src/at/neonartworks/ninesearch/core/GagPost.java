package at.neonartworks.ninesearch.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**********************************************************************
 * | Project : 9Gag Search API
 *
 * Program name : GagPost.java
 *
 * Author : Florian Wagner aka. neonartworks
 *
 * Date created : 06.06.2017
 *
 * Purpose : GagPost class contains information like image, thumbnail, upvotes
 * of a post.
 *
 * Revision History : |
 **********************************************************************/

public class GagPost {

	private String gag_id;
	private String gag_thumbnail;
	private String gag_image;
	private String gag_title;
	private String gag_link;
	private int gag_comments;
	private int gag_votes;

	public GagPost() {

	}

	/**
	 * Constructor of a GagPost. GagPosts store all parameters necessary for a
	 * 9Gag post.
	 * 
	 * @param id
	 *            the 9gag post id
	 * @param thumbnail
	 *            the 9gag post thumbnail
	 * @param image
	 *            the 9gag post image, the actual image of the post
	 * @param title
	 *            the title given by the user who posted the image
	 * @param link
	 *            the link to the 9gag post
	 * @param comments
	 *            the number of comments
	 * @param votes
	 *            the number of upvotes
	 */
	public GagPost(String id, String thumbnail, String image, String title, String link, int comments, int votes) {

		this.setGag_id(id);
		this.setGag_thumbnail(thumbnail);
		this.setGag_image(image);
		this.setGag_title(title);
		this.setGag_link(link);
		this.setGag_comments(comments);
		this.setGag_votes(votes);

	}

	public String getGag_id() {
		return gag_id;
	}

	private void setGag_id(String gag_id) {
		this.gag_id = gag_id;
	}

	public String getGag_thumbnail() {
		return gag_thumbnail;
	}

	private void setGag_thumbnail(String gag_thumbnail) {
		this.gag_thumbnail = gag_thumbnail;
	}

	public String getGag_image() {
		return gag_image;
	}

	private void setGag_image(String gag_image) {
		this.gag_image = gag_image;
	}

	public String getGag_title() {
		return gag_title;
	}

	private void setGag_title(String gag_title) {
		this.gag_title = gag_title;
	}

	public int getGag_comments() {
		return gag_comments;
	}

	private void setGag_comments(int gag_comments) {
		this.gag_comments = gag_comments;
	}

	public int getGag_votes() {
		return gag_votes;
	}

	private void setGag_votes(int gag_votes) {
		this.gag_votes = gag_votes;
	}

	public String getGag_link() {
		return gag_link;
	}

	private void setGag_link(String gag_link) {
		this.gag_link = gag_link;
	}

	/**
	 * Gets the image of the 9Gag post. The image has the original size from
	 * 9Gag.
	 * 
	 * @return the 9Gag-post image as BufferedImage
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public BufferedImage getImage() throws MalformedURLException, IOException {
		return ImageIO.read(new URL(getGag_image()));
	}
	/**
	 * Gets the thumbnail of the 9Gag post. The image has the original size from
	 * 9Gag.
	 * 
	 * @return the 9Gag-post thumbnail as BufferedImage
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public BufferedImage getThumbnail() throws MalformedURLException, IOException {
		return ImageIO.read(new URL(getGag_thumbnail()));
	}

	@Override
	public String toString() {
		return "GagPost: gag_id=" + gag_id + ", gag_thumbnail=" + gag_thumbnail + ", gag_image=" + gag_image
				+ ", gag_title=" + gag_title + ", gag_link=" + gag_link + ", gag_comments=" + gag_comments
				+ ", gag_votes=" + gag_votes;
	}

}