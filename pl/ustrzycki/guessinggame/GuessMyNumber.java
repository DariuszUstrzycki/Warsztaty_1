/*Napisz prostą grę w zgadywanie liczb. Komputer musi wylosować liczbę w zakresie od 1 do 100. Następnie:

Zadać pytanie: "Zgadnij liczbę" i pobrać liczbę z klawiatury.

Sprawdzić, czy wprowadzony napis, to rzeczywiście liczba i w razie błędu wyświetlić komunikat "To nie jest liczba", po czym wrócić do pkt. 1

Jeśli liczba podana przez użytkownika jest mniejsza niż wylosowana, wyświetlić komunikat "Za mało!", po czym wrócić do pkt. 1.
Jeśli liczba podana przez użytkownika jest większa niż wylosowana, wyświetlić komunikat "Za dużo!", po czym wrócić do pkt. 1.
Jeśli liczba podana przez użytkownika jest równa wylosowanej, wyświetlić komunikat "Zgadłeś!", po czym zakończyć działanie programu.
*/

package pl.ustrzycki.guessinggame;

import java.util.Random;
import java.util.Scanner;

public class GuessMyNumber {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		boolean done = false;
		int max = 100;
		int min = 1;

		Random generator = new Random();
		int numToGuess = generator.nextInt(max) + min; 

		do {
			// read data
			int number = readInt(sc, min, max);

			// interpret data
			if (number > numToGuess) {
				displayMessage("Too much!");
			} else if (number < numToGuess) {
				displayMessage("Too little!");
			} else {
				displayMessage("You got it!");
				done = true;
			}

		} while (!done);
	}

	private static int readInt(Scanner sc, int min, int max) {

		int num = 0;
		System.out.println("Guess my number from " + min + " to " + max + ":");

		while (!sc.hasNextInt()) {
			System.out.println("This is not a number! Please try again");
			sc.next(); // clear scanner
		}

		num = sc.nextInt();
		return num;
	}

	private static void displayMessage(String message) {
		System.out.println(message);
	}
}
