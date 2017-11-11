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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LottoSimulator {

	private static List<Integer> userNums = new ArrayList<>();
	private static List<Integer> lottoNums = new ArrayList<>();
	private static final int MAX_LOTTO_NUMBER = 49;
	private static final int MIN_LOTTO_NUMBER = 1;
	private static final int NUMS_TO_DRAW = 6;

	public static void main(String[] args) {

		display("Welcome to the LOTTO simulator! Please enter 6 numbers.");
		userNums = get6UserNumbers();
		lottoNums = draw6LottoNumbers();
		display(LottoSimulator.info());

	}

	/**
	 * Reades 6 user numbers and puts them in a List
	 */
	private static List<Integer> get6UserNumbers() {
		Scanner sc = new Scanner(System.in);
		List<Integer> sixNums = new LinkedList<>();
		int count = 1;
		do {
			boolean readAgain = true;
			int userInput = MIN_LOTTO_NUMBER - 1; // invalid lottery number

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

	private static List<Integer> draw6LottoNumbers() {
		// display("6 lotto numbers: " + lottoPool().subList(MIN_LOTTO_NUMBER -
		// 1, NUMS_TO_DRAW).toString());
		return lottoPool().subList(MIN_LOTTO_NUMBER - 1, NUMS_TO_DRAW);
	}

	private static boolean isValidNumber(int possibleDuplicate) {
		return !userNums.contains(possibleDuplicate)
				&& (possibleDuplicate <= MAX_LOTTO_NUMBER && possibleDuplicate >= MIN_LOTTO_NUMBER);
	}

	private static void display(String message) {
		System.out.println(message);
	}

	public static String info() {
		return "LottoSimulator:\n Lotto numbers are " + lottoNums.toString() + ".\nUser numbers are " + userNums.toString();
	}

}
