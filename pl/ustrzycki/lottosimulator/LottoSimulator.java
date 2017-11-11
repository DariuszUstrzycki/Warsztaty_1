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
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LottoSimulator {
	
	

	private static List<Integer> listOfNums = new ArrayList<>();


	public static void main(String[] args) {
		
		LottoSimulator simulator = new LottoSimulator();
		Scanner sc = new Scanner(System.in);
		boolean done = false;
		int max = 49;
		int min = 1;

		Random generator = new Random();
		
		display("Welcome to the LOTTO simulator! You will have to choose 6 numbers.");
		
		int draws = 0;
		do {
			
			boolean repeat = true;
			int userNumber = -1;
			while (repeat) {
				userNumber = readInt(sc, min, max);
				if (!isNumberCorrect(userNumber)){
					display("Your number is not in the range 1-49 or has already been entered!");
					repeat = true;
				} else {
					repeat = false;	
				}
			}
			listOfNums.add(userNumber);
			draws++;

		} while (draws < 6);
		
		Collections.sort(listOfNums);
		display("Your numbers are " + listOfNums.toString());
		drawLottonumbers();
		display( "Lotto numbers are " + drawLottonumbers().toString());
		
		

	}

	private static boolean isNumberCorrect(int number) {
		return !listOfNums.contains(number) && (number <= 49 && number > 0);

	}

	private static int readInt(Scanner sc, int min, int max) {

		int num = 0;
		System.out.println("Enter a number int the range " + min + " to " + max + ":");

		while (!sc.hasNextInt()) {
			System.out.println("This is not a number! Please try again");
			sc.next(); // clear scanner
		}

		num = sc.nextInt();
		return num;
	}
	
	private static void display(String message){
		System.out.println(message);		
	}
	
	private static List<Integer> drawLottonumbers() {
		
		Integer[] arr_1_49 = new Integer[49];
		for (int i = 0; i < arr_1_49.length; i++) {
			arr_1_49[i] = Integer.valueOf(i+1);			
		}
		System.out.println("Before shuffling: " + Arrays.toString(arr_1_49));
		Collections.shuffle(Arrays.asList(arr_1_49)); // shuffle willnot work wit int[] althoughcode will compile
		
		//System.out.println(Arrays.toString(a1));
		System.out.println("After shuffling: " + Arrays.toString(arr_1_49));
		
		;
		List<Integer> lotto = Arrays.asList(Arrays.copyOf(arr_1_49, 6));
		
		//= Arrays.asList(arr_1_49);
		
		return lotto;
		
	}

}
