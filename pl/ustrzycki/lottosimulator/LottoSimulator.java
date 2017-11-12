/*Warsztat: Symulator LOTTO.

Jak wszystkim wiadomo, LOTTO to gra liczbowa polegająca na losowaniu 6 liczb z zakresu 1-49. 
Zadaniem gracza jest poprawne wytypowanie losowanych liczb. 
Nagradzane jest trafienie 3, 4, 5 lub 6 poprawnych liczb.

Napisz program, który:

1.zapyta o typowane liczby, przy okazji sprawdzi następujące warunki:
2.czy wprowadzony ciąg znaków jest poprawną liczbą,
3.czy użytkownik nie wpisał tej liczby już poprzednio,
4.czy liczba należy do zakresu 1-49,
5.po wprowadzeniu 6 liczb, posortuje je rosnąco i wyświetli na ekranie,
6.wylosuje 6 liczb z zakresu i wyświetli je na ekranie,

7.poinformuje gracza, czy trafił przynajmniej "trójkę".
DONE

8.Aby wylosować 6 liczb z zakresu 1-49 bez powtórzeń możemy utworzyć tablicę z wartościami 1-49, wymieszać jej zawartość i pobrać pierwsze 6 elementów.

Poniższy kod powinien Ci pomóc:

Integer[] arr = new Integer[49];
for (int i = 0; i < arr.length; i++) {
	arr[i] = i;
}
System.out.println(Arrays.toString(arr));
Collections.shuffle(Arrays.asList(arr));
System.out.println(Arrays.toString(arr));
Możesz również losować liczby z określonego zakresu (sprawdź w snippetach jak to wykonać) 
- jeżeli wybierzesz takie rozwiązanie, pamiętaj o sprawdzaniu czy dana wartość nie została wcześniej wylosowana.*/

package pl.ustrzycki.lottosimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class LottoSimulator {

	private static Set<Integer> playerNums = new HashSet<>();
	private static Set<Integer> lottoNums = new HashSet<>();
	private static final int MAX_LOTTO_NUMBER = 49;
	private static final int MIN_LOTTO_NUMBER = 1;
	private static final int NUMS_TO_DRAW = 6;

	public static void main(String[] args) {
		display("Welcome to the LOTTO simulator! Please enter 6 numbers.");
		playerNums = get6playerNumbers();
		lottoNums = draw6LottoNumbers();
		display(getDrawMessage());

		int noOfMatches = matches(playerNums, lottoNums);
		display(getMatchesMessage(noOfMatches));
	}

	private static Set<Integer> sortAscending(Set<Integer> numbers) {
		TreeSet<Integer> sorted = new TreeSet<>();
		sorted.addAll(numbers);
		return sorted;
	}

	private static String getDrawMessage() {
		return "\nLOTTO SIMULATOR RESULTS:\nLotto numbers are " + sortAscending(lottoNums).toString()
				+ "\nplayer numbers are  " + sortAscending(playerNums).toString() + "\n";
	}

	private static String getMatchesMessage(int number) {
		String matches = "";
		switch (number) {
		case 0:
			matches = "no number.";
			break;
		case 1:
			matches = "1 number: " + sortAscending(playerNums).toString();
			break;
		default:
			matches = number + " numbers: " + sortAscending(playerNums).toString();
		}
		return "You have guessed " + matches;
	}

	/**
	 * @return the common part between the lotto numbers and player numbers
	 */
	private static int matches(Set<Integer> playerNums, Set<Integer> lottoNums) {
		playerNums.retainAll(lottoNums);
		return playerNums.size();
	}

	/**
	 * Reads 6 player numbers and puts them in a List
	 */
	private static Set<Integer> get6playerNumbers() {
		Scanner sc = new Scanner(System.in);
		int count = 1;

		do {
			boolean readAgain = true;
			Integer playerInput = MIN_LOTTO_NUMBER - 1; // invalid lottery
														// number

			while (readAgain) {
				playerInput = readInt(sc);
				if (!isValidNumber(playerInput)) {
					display("Your number is not in the range 1-49 or has already been entered!");
					readAgain = true;
				} else {
					readAgain = false;
				}
			}
			playerNums.add(playerInput);
			count++;

		} while (count <= NUMS_TO_DRAW);

		sc.close();
		return playerNums;
	}

	/**
	 * Reads a number entered by the player
	 */
	private static int readInt(Scanner sc) {
		int num = 0;
		System.out.println("Enter a number int the range " + MIN_LOTTO_NUMBER + " to " + MAX_LOTTO_NUMBER + ":");

		while (!sc.hasNextInt()) {
			System.out.println("This is not a number! Please try again");
			sc.next(); // clear scanner
		}

		num = sc.nextInt();
		return num;
	}

	/**
	 * Generates a pool of numbers from 1-49 and shuffles them
	 */
	private static List<Integer> lottoPool() {
		List<Integer> lottoPool = new ArrayList<>();

		for (int i = 0; i < MAX_LOTTO_NUMBER; i++) {
			lottoPool.add(MIN_LOTTO_NUMBER + i);
		}

		Collections.shuffle(lottoPool);
		return lottoPool;
	}

	/**
	 * @return first six numbers from the pool of shuffled numbers in the range
	 *         of 1-49
	 */
	private static Set<Integer> draw6LottoNumbers() {
		return new HashSet<Integer>(lottoPool().subList(MIN_LOTTO_NUMBER - 1, NUMS_TO_DRAW));
	}

	/**
	 * @param possibleDuplicate
	 *            number entered by the use, not yet added to the List
	 * @return true if the possibleDuplicate is in the right range and not
	 *         already in the List
	 */
	private static boolean isValidNumber(Integer possibleDuplicate) {
		return !playerNums.contains(possibleDuplicate)
				&& (possibleDuplicate <= MAX_LOTTO_NUMBER && possibleDuplicate >= MIN_LOTTO_NUMBER);
	}

	private static void display(String message) {
		System.out.println(message);
	}

}
