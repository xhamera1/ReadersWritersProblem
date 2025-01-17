package pl.edu.agh.kis.pz1;

import java.util.Arrays;


/**
 * The Main class serves as the entry point for the Readers-Writers simulation.
 * It initializes a shared Library resource and creates reader and writer threads.
 * The number of readers and writers can be passed as command-line arguments or defaults to predefined values.
 */
public class Main {
    private static int liczbaCzytelnikow = 10;
    private static int liczbaPisarzy = 3;
    /**
     * The main method initializes and starts the simulation of readers and writers accessing the library.
     *
     * @param args Command-line arguments to specify the number of readers and writers:
     *     args[0]: Number of readers (must be an integer greater than 0).
     *     args[1]: Number of writers (must be an integer greater than 0).
     *    If no arguments or invalid arguments are provided, default values are used.
     */
    public static void main(String[] args) {

        handleArgs(args);

        Library library = new Library();

        Reader[] readers = new Reader[liczbaCzytelnikow];
        for (int i=0; i<liczbaCzytelnikow; i++){
            readers[i] = new Reader(i+1, library);
        }

        Writer[] writers = new Writer[liczbaPisarzy];
        for (int i=0; i<liczbaPisarzy; i++){
            writers[i] = new Writer(i+1, library);
        }

        Arrays.stream(readers).
                forEach(Thread::start);

        Arrays.stream(writers).
                forEach(Thread::start);


    }

    public static void handleArgs(String[] args) {
        try {

            if (!(args.length == 2 || args.length == 0)) {
                throw new NumberFormatException("Niepoprawna liczba argumentów. Podaj 2 liczby całkowite.");
            }

            if (args.length == 2) {
                liczbaCzytelnikow = Integer.parseInt(args[0]);
                liczbaPisarzy = Integer.parseInt(args[1]);
            }

            if (liczbaCzytelnikow < 1 || liczbaPisarzy < 1) {
                throw new IllegalStateException("Musi byc co najmniej jeden pisarz i jeden czytelnik.");
            }

            System.out.println("Liczba czytelnikow: " + liczbaCzytelnikow);
            System.out.println("Liczba pisarzy: " + liczbaPisarzy);

        } catch (NumberFormatException ex) {
            System.err.println("Bledny format liczby czytelnikow lub pisarzy\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3");
            liczbaCzytelnikow = 10;
            liczbaPisarzy = 3;
        } catch (IllegalStateException ex) {
            System.err.println(ex.getMessage() + "\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3");
            liczbaCzytelnikow = 10;
            liczbaPisarzy = 3;
        } catch (Exception e) {
            liczbaCzytelnikow = 10;
            liczbaPisarzy = 3;
        }
    }
}