/*Warsztat: Wyszukiwarka najpopularniejszych słów

1.Wyszukaj w popularnych serwisach internetowych nagłówków artykułów, 
2.Zapisz pojedyncze słowa w nich występujące do pliku o nazwie popular_words.txt. 
skip (3.Wywołaj pobieranie dla wybranych serwisów internetowych.)
4.Wczytaj utworzony plik popular_words.txt i na jego podstawie 
5.utwórz plik most_popular_words.txt, który zawierać będzie 10 najbardziej popularnych słów.
skip (6. Utwórz tablicę elementów wykluczonych np. i, lub , ewentualnie pomiń wszystkie elementy 3-znakowe.)
*/

package pl.ustrzycki.mostpopular;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MostPopularWords {

	public static void main(String[] args) {
		// for testing:
		/*
		 * String titles = "Krowa, zjadła; krowa\n" +
		 * " I widziała.go-go \\\"krowa: krowa- i\n" +
		 * "bulion? ,zupa\\\" zupa!";
		 */

		// dla https://www.wp.pl/ - <a> wth attribute title?
		String onetHeadlines = getWebpageHeadlines("http://www.onet.pl/", "span.title");
		String interiaHeadlines = getWebpageHeadlines("http://www.interia.pl/", "li.news-li a.news-a");
		String allHeadlines = "ONET: " + onetHeadlines + "\nINTERIA: \n\n" + interiaHeadlines;
		// display(allHeadlines);
		// display("\n AFTER CLEARING !!!\n");
		String toRemove = " #|[],;-:\\.!?\"\\\r\\\t///";
		String clearedHeadlines = removeNonWordChars(allHeadlines, toRemove);
		display(clearedHeadlines);

		// done --------------------
		Map<String, Integer> words = frequency(clearedHeadlines);
		//displayMapKeyAscend(words);

		displayMapValueDesc(words, 20);
		// -----------------------------
		/*
		 * writeDataToFile(words, "popular_words.txt"); String popularWords =
		 * readFromFile("popular_words.txt"); Map<String,Integer>
		 * mostPopularWords = selectMostPopular(popularWords, 10);
		 * writeDataToFile(mostPopularWords, "most_popular_words.txt");
		 */
	}

	private static boolean censor(String str) {

		str = str.toLowerCase();
		String[] forbiddenWords = { "się", "czy", "nie", "nas", "onet", "interia", "jak", "jest", "ponad", "dla", "bez",
				"dziś", "raz", "dwóch", "trzech", "pod", "może", "nawet", "nad", "będzie"};
		Set<String> forbiddenSet = new HashSet<>(Arrays.asList(forbiddenWords));

		boolean censored = false;

		if (str.length() < 3)
			censored = true;

		if (NumberUtils.isDigits(str))
			censored = true;

		if (forbiddenSet.contains(str))
			censored = true;

		return censored;
	}

	/**
	 * Reads all spans with title class from a webpage
	 * 
	 * @return string with collected data
	 */
	private static String getWebpageHeadlines(String url, String selection) {

		Connection connect = Jsoup.connect(url);
		StringBuilder builder = new StringBuilder();
		try {
			Document document = connect.get();
			Elements links = document.select(selection);
			for (Element elem : links) {
				builder.append(elem.text() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	private static String removeNonWordChars(String raw, String toRemove) {

		boolean keepNewLines = false;
		StringTokenizer tokenizer;

		if (keepNewLines)
			tokenizer = new StringTokenizer(raw, toRemove + "\\\n", false); // false
																			// -
																			// dont
																			// include
																			// delimiters
		else
			tokenizer = new StringTokenizer(raw, toRemove, false);

		StringBuilder builder = new StringBuilder();
		while (tokenizer.hasMoreTokens())
			builder.append(tokenizer.nextToken() + " "); // \\\n - this removes
															// \n !!!

		return builder.toString();
	}

	/**
	 * Tokenizes string on spaces, counts each word's frequency in the string
	 * and creates word/frequency map
	 */
	private static Map<String, Integer> frequency(String text) {

		boolean ignoreCase = true;

		Map<String, Integer> tokens = new HashMap<>();
		StringTokenizer tokenizer = new StringTokenizer(text);

		while (tokenizer.hasMoreTokens()) {
			String word = tokenizer.nextToken();

			if (censor(word)) // to see censored words change to !censor(word
				continue;

			if (ignoreCase)
				word = word.toLowerCase();

			if (tokens.containsKey(word)) {
				int count = tokens.get(word); // get current count
				tokens.put(word, count + 1); // increment frequency
			} else {
				tokens.put(word, 1); // add new word with count 1
			}
		}

		return tokens;
	}

	private static void writeDataToFile(Map<String, Integer> words, String fileName) {

	}

	private static String readFromFile(String fileName) {
		return null;
	}

	private static Map<String, Integer> selectMostPopular(String popularWords, int wordLimit) {
		return null;
	}

	private static void displayMapKeyAscend(Map<String, Integer> map) {

		Set<String> keys = map.keySet();

		TreeSet<String> sortedKeys = new TreeSet<>(keys); // sort keys
		System.out.printf("%nMost popular words in onet.pl and wp.pl headlines:%nWord\t\t\tFrequency%n");

		for (String key : sortedKeys)
			System.out.printf("%-20s%12s%n", key, map.get(key));

	}

	private static void displayMapValueDesc(Map<String, Integer> unsortMap, int limit) {

		boolean ascending = false;
		Map<String, Integer> sortedMapAsc = sortByComparator(unsortMap, ascending);
		Set<String> keys = sortedMapAsc.keySet();
		System.out.printf("%nMost popular words in onet.pl and wp.pl headlines:%nWord\t\t\tFrequency%n");
		
		int loop = 0;
		for (String key : keys){
			if(loop++ >= limit)
				break;
			System.out.printf("%-20s%12s%n", key, sortedMapAsc.get(key));
		}
	}

	private static void display(String message) {
		System.out.println(message);
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (order)
					return o1.getValue().compareTo(o2.getValue());
				else
					return o2.getValue().compareTo(o1.getValue());
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
