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
		int secretNumber = generator.nextInt(max) + min; 

		do {
			int guess = readInt(sc, min, max);

			if (guess > secretNumber) {
				display("Too much!");
			} else if (guess < secretNumber) {
				display("Too little!");
			} else {
				display("You got it!");
				done = true;
			}

		} while (!done);
		
		sc.close();
	}

	private static int readInt(Scanner sc, int min, int max) {

		display("Guess my number from " + min + " to " + max + ":");

		while (!sc.hasNextInt()) {
			display("This is not a number! Please try again");
			sc.next(); // clear scanner
		}

		return sc.nextInt();
	}

	private static void display(String message) {
		System.out.println(message);
	}
}
