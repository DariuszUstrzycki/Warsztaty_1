#### Warsztat 1: Guess My Number.

Napisz prostą grę w zgadywanie liczb. Komputer musi wylosować liczbę w zakresie od 1 do 100. Następnie:

1. Zadać pytanie: "Zgadnij liczbę" i pobrać liczbę z klawiatury.
2. Sprawdzić, czy wprowadzony napis, to rzeczywiście liczba i w razie błędu wyświetlić komunikat "To nie jest liczba", po czym wrócić do pkt. 1
3. Jeśli liczba podana przez użytkownika jest mniejsza niż wylosowana, wyświetlić komunikat "Za mało!", po czym wrócić do pkt. 1.
4. Jeśli liczba podana przez użytkownika jest większa niż wylosowana, wyświetlić komunikat "Za dużo!", po czym wrócić do pkt. 1.
5. Jeśli liczba podana przez użytkownika jest równa wylosowanej, wyświetlić komunikat "Zgadłeś!", po czym zakończyć działanie programu.

##### Przykład:
```
Zgadnij liczbę: cześć
To nie jest liczba.
Zgadnij liczbę: 50
Za mało!
Zgadnij liczbę: 75
Za dużo!
Zgadnij liczbę: 63
Zgadłeś!
```

#### Warsztat 1: LOTTO Simulator.

Jak wszystkim wiadomo, LOTTO to gra liczbowa polegająca na losowaniu 6 liczb z zakresu 1-49. Zadaniem gracza jest poprawne wytypowanie losowanych liczb. Nagradzane jest trafienie 3, 4, 5 lub 6 poprawnych liczb.

Napisz program, który:

* zapyta o typowane liczby, przy okazji sprawdzi następujące warunki:
    * czy wprowadzony ciąg znaków jest poprawną liczbą,
    * czy użytkownik nie wpisał tej liczby już poprzednio,
    * czy liczba należy do zakresu 1-49,
* po wprowadzeniu 6 liczb, posortuje je rosnąco i wyświetli na ekranie,
* wylosuje 6 liczb z zakresu i wyświetli je na ekranie,
* poinformuje gracza, czy trafił przynajmniej "trójkę".
