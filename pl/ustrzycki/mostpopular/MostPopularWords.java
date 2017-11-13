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
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MostPopularWords {

	public static void main(String[] args) {

		String titles = getTitlesFromWeb("onet.pl"); 
		Map<String,Integer> words = frequency(titles); 
		writeDataToFile(words, "popular_words.txt"); 
		String popularWords = readFromFile("popular_words.txt"); 
		Map<String,Integer> mostPopularWords = selectMostPopular(popularWords, 10); 
		writeDataToFile(mostPopularWords, "most_popular_words.txt");

	}

	private static String getTitlesFromWeb(String url) {

		Connection connect = Jsoup.connect("http://www.onet.pl/");
		try {
			Document document = connect.get();
			Elements links = document.select("span.title");
			for (Element elem : links) {
				System.out.println(elem.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, Integer> frequency(String titles) {
		return null;
	}

	private static void writeDataToFile(Map<String, Integer> words, String fileName) {

	}

	private static String readFromFile(String fileName) {
		return null;
	}

	private static Map<String, Integer> selectMostPopular(String popularWords, int wordLimit) {
		return null;
	}

}
