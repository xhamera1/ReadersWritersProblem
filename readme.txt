======================================================================
 README - PROBLEM CZYTELNIKÓW I PISARZY (READERS-WRITERS PROBLEM)
======================================================================

OPIS PROJEKTU
-------------
Niniejszy projekt stanowi implementację klasycznego problemu współbieżności,
zwanego Problemem Czytelników i Pisarzy (Readers-Writers Problem).

Główne założenia:
1. Tylko jeden pisarz może w danym momencie mieć wyłączny dostęp do zasobu
   (biblioteki).
2. Jednocześnie w bibliotece może przebywać maksymalnie 5 czytelników.
3. Zapewniona jest ochrona przed zagłodzeniem (starvation), co oznacza, że
   żaden wątek (czytelnik ani pisarz) nie czeka bez końca.

W projekcie zdefiniowano następujące klasy:
- Main – klasa uruchomieniowa (tworzy obiekt biblioteki i uruchamia wątki).
- Library – zarządza dostępem do zasobu (czytelni) przy użyciu semaforów.
- Reader – wątek-czytelnik (dziedziczy po Thread).
- Writer – wątek-pisarz (dziedziczy po Thread).
- ChronologicalLogger oraz LoggingThread – obsługa asynchronicznego logowania
  chronologicznego.

FUNKCJONALNOŚCI
---------------
1. PARAMETRY URUCHAMIANIA
   * Program pozwala na przekazanie w linii poleceń liczby czytelników i pisarzy:
       java -jar main/target/main-1.0-SNAPSHOT.jar <liczba_czytelników> <liczba_pisarzy>
   * Jeśli parametry nie zostaną podane lub są niepoprawne, używane są wartości
     domyślne:
     - 10 czytelników
     - 3 pisarzy

2. SYNCHRONIZACJA
   * Wykorzystano dwa semafory z pakietu java.util.concurrent:
       - Semafor ograniczający liczbę jednoczesnych czytelników (maks. 5).
       - Semafor uniemożliwiający więcej niż jednemu pisarzowi pisanie
         równocześnie.
   * Zapewniono brak zagłodzenia (starvation) – każdy wątek, czytelnika czy
     pisarza, dostanie w rozsądnym czasie dostęp do zasobu.

3. LOGOWANIE
   * Klasy ChronologicalLogger i LoggingThread gromadzą i wypisują wszystkie
     informacje o wejściach i wyjściach wątków, liczbie czytelników/pisarzy
     w bibliotece, a także w kolejce.
   * Logi wyświetlane są w porządku chronologicznym.

4. TESTY JEDNOSTKOWE
   * Projekt posiada zestaw testów (pokrycie: 89.8%).
   * Analiza SonarQube nie wykazała otwartych problemów dotyczących bezpieczeństwa,
     niezawodności i utrzymywalności.

OMÓWIENIE ZAIMPLEMENTOWANEGO ALGORYTMU
--------------------------------------
Zastosowano dwa semafory, które kontrolują dostęp wątków (czytelników i pisarzy)
do wspólnego zasobu (biblioteki). Poniżej przedstawiono główne aspekty
działania algorytmu:

1. Czytelnicy:
   * Mogą działać współbieżnie (do 5 jednocześnie), wywołując metody
     requestRead(...) i finishRead(...).
   * Jeżeli czytelników w czytelni jest 0, w momencie wejścia pierwszego
     czytelnika blokowany jest semafor pisarzy (aby pisarz nie mógł wejść
     w tym samym czasie).

2. Pisarze:
   * Wyłączny dostęp do czytelni – w jednym momencie może pisać tylko
     jeden pisarz (semafor o wartości 1).
   * Pisarz wywołuje metody requestWrite(...) i finishWrite(...).

3. Ochrona przed zagłodzeniem:
   * Dzięki kolejkowaniu w semaforach, żaden wątek (czytelnik/pisarz) nie
     zostanie zablokowany na stałe.
   * Maksymalny czas przebywania w czytelni jest ograniczony do 3 sekund,
     co umożliwia rotację wątków w dostępie do zasobu.

4. Logowanie:
   * Wszelkie próby wejścia (i wyjścia) czytelników/pisarzy są rejestrowane
     przez ChronologicalLogger wraz z aktualną liczbą czytelników oraz
     pisarzy w czytelni i w kolejce.

INSTALACJA I URUCHOMIENIE
-------------------------
1. KLONOWANIE REPOZYTORIUM
   git clone <URL_repozytorium>

2. BUDOWANIE PROJEKTU
   * Wymagana Java 17 lub wyższa oraz Maven.
   * W głównym katalogu projektu (lub odpowiednim module) wykonaj:
       mvn clean install

3. URUCHOMIENIE APLIKACJI
   * Przejdź do katalogu docelowego (np. main/target) i uruchom:
       java -jar main-1.0-SNAPSHOT.jar
   * Opcjonalnie podaj parametry, np.:
       java -jar main-1.0-SNAPSHOT.jar 5 2
     co uruchomi 5 czytelników i 2 pisarzy.

4. URUCHAMIANIE TESTÓW
   * Wykonaj:
       mvn test
   * Wygenerowane raporty pokażą wysokie pokrycie testami i brak krytycznych
     problemów w SonarQube.

TECHNOLOGIE
-----------
- Język: Java 17
- System budowania: Maven
- SonarQube: analiza jakości kodu