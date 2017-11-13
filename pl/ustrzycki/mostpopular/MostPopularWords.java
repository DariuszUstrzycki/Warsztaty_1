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

		/*String titles = "Krowa, zjadła; krowa\n"
		+ " I widziała.go-go \\\"krowa: krowa- i\n"
		+ "bulion? ,zupa\\\" zupa!";*/
		
		String titles = getTitlesFromWeb("onet.pl"); 
		display(titles);
		display("\nTu koniec!!!\n\n\n\n\n");
		//String clearedTitles = prepareSplit(titles);
		/*	//display(clearedTitles);
		Map<String,Integer> words = frequency(clearedTitles);
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
	private static String getTitlesFromWeb(String url) {

		Connection connect = Jsoup.connect("http://www.onet.pl/");
		StringBuilder builder = new StringBuilder();
		try {
			Document document = connect.get();
			Elements links = document.select("span.title");
			for (Element elem : links) {
				builder.append(elem.text() + "\n");
				//System.out.println(elem.tex + t());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private static String prepareSplit(String raw) {
		System.out.println("this is raw: " + raw);
		
		StringTokenizer tokenizer = new StringTokenizer(raw, " ,;-:\\.!?\\\n\"\\\r\\\t", false);
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
