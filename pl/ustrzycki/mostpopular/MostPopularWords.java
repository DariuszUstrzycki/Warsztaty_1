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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MostPopularWords {

	public static void main(String[] args) {
		// for testing:
		/*String titles = "Krowa, zjadła; krowa\n"
		+ " I widziała.go-go \\\"krowa: krowa- i\n"
		+ "bulion? ,zupa\\\" zupa!";*/
		
		
		//dla https://www.wp.pl/ - <a> wth attribute title?
		String onetHeadlines = getWebpageHeadlines("http://www.onet.pl/", "span.title");
		String interiaHeadlines = getWebpageHeadlines("http://www.interia.pl/", "li.news-li a.news-a"); 
		String allHeadlines = "ONET: " + onetHeadlines + "\nINTERIA: \n\n" + interiaHeadlines;
			display(allHeadlines);
			display("\n AFTER CLEARING !!!\n");
		String toRemove = " ,;-:\\.!?\"\\\r\\\t";
		String clearedHeadlines = removeNonWordChars(allHeadlines, toRemove);
		display(clearedHeadlines);
		// done --------------------
		/*Map<String,Integer> words = frequency(clearedTitles);
			//displayMap(words);
			//-----------------------------
		
		writeDataToFile(words, "popular_words.txt"); 
		String popularWords = readFromFile("popular_words.txt"); 
		Map<String,Integer> mostPopularWords = selectMostPopular(popularWords, 10); 
		writeDataToFile(mostPopularWords, "most_popular_words.txt");
*/
	}

	/**
	 * Reads all spans with title class from a webpage
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
		
		if(keepNewLines)
			tokenizer = new StringTokenizer(raw, toRemove + "\\\n", false);  // false - dont include delimiters
		else
			tokenizer = new StringTokenizer(raw, toRemove, false); 
		 
		StringBuilder builder = new StringBuilder();
		while(tokenizer.hasMoreTokens())
			builder.append(tokenizer.nextToken().toLowerCase() + " "); //  \\\n - this removes \n !!!
		
		return builder.toString();
	}
	
	
	/**
	 * Tokenizes string on spaces, counts each word's frequency in the string 
	 * and creates word/frequency map 
	 */
	private static Map<String, Integer> frequency(String text) {
		
		Map<String, Integer> tokens = new HashMap<>();
		StringTokenizer tokenizer = new StringTokenizer(text);
		
		while(tokenizer.hasMoreTokens()){
			String word = tokenizer.nextToken().toLowerCase(); 
			if(tokens.containsKey(word)){	
				int count = tokens.get(word); // get current count
				tokens.put(word, count + 1); // increment frequency
			}else{
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
	
	private static void displayMap(Map<String,Integer> map){
		
		Set<String> keys = map.keySet();
		
		TreeSet<String> sortedKeys = new TreeSet<>(keys); // sort keys
		System.out.printf("%nMap contains:%nKey\t\t\tValue%n");
		
		for(String key : sortedKeys)
			System.out.printf("%-15s%12s%n", key, map.get(key));
		
	}
	
	private static void display(String message){
		System.out.println(message);		
	}

}
