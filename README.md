# 9-Search
9-Search is an unofficial 9GAG search api for Java. 

## Features
9-Search allowes to search and download images and posts directly from 9Gag.
It is very easy to use -> see examples below!

## Limitations
It is only possible to download the first 10 posts from 9GAG.

## Download
You can download the .jar library here: https://www.mediafire.com/?bjx612v18584i6v

## Examples

Get the first 10 posts from a section.

```java
package at.neonartworks.ninesearch.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.neonartworks.ninesearch.core.GagPost;
import at.neonartworks.ninesearch.core.GagSearchV2;
import at.neonartworks.ninesearch.utils.GagSection;


public class GetFromSection {

	private static List<GagPost> posts = new ArrayList<GagPost>();

	public static void main(String[] args) {
		GagSearchV2 gs = new GagSearchV2(); // create a new instance of
											// GagSearch.

		try {
			// use the getFromSectoin command to retrieve the posts.
			posts = gs.getFromSection(GagSection.DARK_HUMOR);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// check the retrieved posts by printing everyone to the console.
		for (GagPost p : posts) {
			System.out.println(p.toString());

		}

	}

}
```

Get the first 10 posts from a search query.

```java
package at.neonartworks.ninesearch.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.neonartworks.ninesearch.core.GagPost;
import at.neonartworks.ninesearch.core.GagSearchV2;

public class Search {

	private static List<GagPost> posts = new ArrayList<GagPost>();

	public static void main(String[] args) {
		GagSearchV2 gs = new GagSearchV2(); // create a new instance of
											// GagSearch.

		try {
			// ALWAYS call the initSearch before the search method.
			gs.initSearch("VSauce");

			// use the search method to retrieve the posts.
			posts = gs.search();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// check the retrieved posts by printing everyone to the console.
		for (GagPost p : posts) {
			System.out.println(p.toString());

		}

	}

}
```

## Upgrades
We are currently working on a better library! 
For more information visit: https://github.com/Crimsonbit/JGag
