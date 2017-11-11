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
DONE

7.poinformuje gracza, czy trafił przynajmniej "trójkę".

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

public class LottoSimulator {

	private static Set<Integer> userNums = new HashSet<>();
	private static Set<Integer> lottoNums = new HashSet<>();
	private static final int MAX_LOTTO_NUMBER =  12; // 49;
	private static final int MIN_LOTTO_NUMBER = 1;
	private static final int NUMS_TO_DRAW = 6;

	public static void main(String[] args) {

		display("Welcome to the LOTTO simulator! Please enter 6 numbers.");
		userNums = get6UserNumbers();
		lottoNums = draw6LottoNumbers();
					display("Before matching: " + LottoSimulator.info());
		
		int noOfMatches = matches(userNums, lottoNums);
					display("After matching: " + LottoSimulator.info());
		
					display("Nums in common: " + userNums.size() + " must be the same as " + noOfMatches);

	}
	
	private static int matches (Set<Integer> userNums, Set<Integer> lottoNums) {
		userNums.retainAll(lottoNums);
		return userNums.size();
	}

	/**
	 * Reades 6 user numbers and puts them in a List
	 */
	private static Set<Integer> get6UserNumbers() {
		Scanner sc = new Scanner(System.in);
		Set<Integer> sixNums = new HashSet<>();
		int count = 1;
		do {
			boolean readAgain = true;
			Integer userInput = MIN_LOTTO_NUMBER - 1; // invalid lottery number

			while (readAgain) {
				userInput = readInt(sc);
				if (!isValidNumber(userInput)) {
					display("Your number is not in the range 1-49 or has already been entered!");
					readAgain = true;
				} else {
					readAgain = false;
				}
			}
			sixNums.add(userInput);
			count++;

		} while (count <= NUMS_TO_DRAW);

		sc.close();
		return sixNums;
	}

	/**
	 * utility method reading numbers from the correct range entered by the user
	 * 
	 * @return
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

	private static List<Integer> lottoPool() {

		List<Integer> lottoPool = new ArrayList<>();

		for (int i = 0; i < MAX_LOTTO_NUMBER; i++) {
			lottoPool.add(MIN_LOTTO_NUMBER + i);
		}

				display("pool before shuffling: " + lottoPool.toString());
		Collections.shuffle(lottoPool);
				display("pool after shuffling: " + lottoPool.toString());

		return lottoPool;
	}

	private static Set<Integer> draw6LottoNumbers() {
		
		return new HashSet<Integer>(lottoPool().subList(MIN_LOTTO_NUMBER - 1, NUMS_TO_DRAW));
	} 

	private static boolean isValidNumber(Integer possibleDuplicate) {
			//display("" + possibleDuplicate);
		return !userNums.contains(possibleDuplicate) && (possibleDuplicate <= MAX_LOTTO_NUMBER && possibleDuplicate >= MIN_LOTTO_NUMBER);
	}

	private static void display(String message) {
		System.out.println(message);
	}

	public static String info() {
		return "LottoSimulator:\nLotto numbers are " + lottoNums.toString() + "\nUser numbers are " + userNums.toString();
	}

}
