/*Warsztat: Wyszukiwarka najpopularniejszych słów

1.Wyszukaj w popularnych serwisach internetowych nagłówków artykułów, 
2.Zapisz pojedyncze słowa w nich występujące do pliku o nazwie popular_words.txt. 
skip (3.Wywołaj pobieranie dla wybranych serwisów internetowych.)
4.Wczytaj utworzony plik popular_words.txt 
5. Utwórz tablicę elementów wykluczonych np. i, lub , ewentualnie pomiń wszystkie elementy 3-znakowe.
6.utwórz plik most_popular_words.txt, który zawierać będzie n najbardziej popularnych słów.

NOTES: // dla https://www.wp.pl/ - <a> wth attribute title??
*/

package pl.ustrzycki.mostpopular;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.util.Scanner;
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

	public static final String ONET_URL = "http://www.onet.pl/";
	public static final String INTERIA_URL = "http://www.onet.pl/";

	public static void main(String[] args) {

		// get headlines and put them in a a string
		String onetHeadlines = getWebpageHeadlines(ONET_URL, "span.title");
		String interiaHeadlines = getWebpageHeadlines(INTERIA_URL, "li.news-li a.news-a");
		String combinedHeadlines = "ONET: " + onetHeadlines + "\nINTERIA: \n\n" + interiaHeadlines;

		// format the headlines
		String toRemove = " #|[],;-:\\.!?\"\\\r\\\t///";
		String clearedHeadlines = removeNonWordChars(combinedHeadlines, toRemove);
		String wordsList = splitIntoWords(clearedHeadlines);

		// write to a file, read from the file
		writeDataToFile(wordsList, "popular_words.txt");
		wordsList = readFromFile("popular_words.txt");

		// filter the words and work out n most popular words
		Map<String, Integer> wordsMap = frequency(wordsList);
		int wordsToDisplay = 20;
		// save to a new file
		writeDataToFile(wordsMap, "most_popular_words.txt", wordsToDisplay);

		// ---------------for testing only:--------------------------
		// displayMapKeyAscend(wordsMap);
		// displayMapValueDesc(wordsMap, 20);

	}

	private static String splitIntoWords(String text) {

		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		StringBuilder builder = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			builder.append(tokenizer.nextToken() + "\n");
		}

		return builder.toString();
	}

	private static boolean censor(String str) {

		String[] forbiddenWords = { "się", "czy", "nie", "nas", "onet", "interia", "jak", "jest", "ponad", "dla", "bez",
				"dziś", "raz", "dwóch", "trzech", "pod", "może", "nawet", "nad", "będzie" };
		Set<String> forbiddenSet = new HashSet<>(Arrays.asList(forbiddenWords));

		str = str.toLowerCase();
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
	 * Reads all spans with 'title' class from a webpage
	 * 
	 * @return string with filtered data
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
			tokenizer = new StringTokenizer(raw, toRemove + "\\\n", false); // dont
																			// include
																			// delimiters
		else
			tokenizer = new StringTokenizer(raw, toRemove, false);

		StringBuilder builder = new StringBuilder();
		while (tokenizer.hasMoreTokens())
			builder.append(tokenizer.nextToken() + " ");

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

			if (censor(word)) // to see censored words just change to
								// (!censor(word))
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

	private static void writeDataToFile(String str, String fileName) {

		try (FileWriter out = new FileWriter(fileName, false)) { // zastapi
			out.append(str);
		} catch (IOException ex) {
			System.out.println("Can't write to the file!");
			ex.printStackTrace();
		}
	}

	private static String readFromFile(String fileName) {

		Path path = Paths.get(fileName); // moze byc Path zamiast File
		StringBuilder builder = new StringBuilder();

		try (Scanner sc = new Scanner(path)) {
			while (sc.hasNextLine()) {
				builder.append(sc.nextLine() + "\n");
			}

		} catch (FileNotFoundException ex) {
			System.out.println("The file is unavailable!");
			ex.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't read from the file!");
			e.printStackTrace();
		}

		return builder.toString();
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
		for (String key : keys) {
			if (loop++ >= limit)
				break;
			System.out.printf("%-20s%12s%n", key, sortedMapAsc.get(key));
		}
	}

	private static void writeDataToFile(Map<String, Integer> unsortMap, String fileName, int limit) {

		boolean ascending = false;
		Map<String, Integer> sortedMapAsc = sortByComparator(unsortMap, ascending);
		Set<String> keys = sortedMapAsc.keySet();
		// String header = String.format(("%nMost popular words in onet.pl and
		// wp.pl headlines:%nWord\t\t\tFrequency%n"));
		// String limited = header;

		try (FileWriter out = new FileWriter(fileName, false)) { // zastapi

			int loop = 0;
			for (String key : keys) {
				out.append(String.format("%-20s%12s%n", key, sortedMapAsc.get(key)));

				if (++loop >= limit)
					break;
			}

		} catch (IOException ex) {
			System.out.println("Can't write to the file!");
			ex.printStackTrace();
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
