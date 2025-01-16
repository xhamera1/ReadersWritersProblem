package pl.edu.agh.kis.pz1;

import java.util.Arrays;



public class Main {
    public static void main(String[] args) {
        int liczbaCzytelnikow = 10;
        int liczbaPisarzy = 3;
        try{
            if (!(args.length == 2 || args.length == 0)){
                throw new NumberFormatException();
            }
            liczbaCzytelnikow = Integer.parseInt(args[0]);
            liczbaPisarzy = Integer.parseInt(args[1]);
            if (liczbaCzytelnikow < 1 || liczbaPisarzy < 1){
                throw new IllegalStateException("Musi byc chociaz jeden pisarz i czytelnik, ");
            }
            System.out.println("Liczba czytelnikow " + liczbaCzytelnikow);
            System.out.println("Liczba pisarzy " + liczbaPisarzy);
        }
        catch(NumberFormatException ex){
            System.err.println("Bledny format liczby czytelnikow lub pisarzy\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3");
        }
        catch(IllegalStateException iex){
            System.err.println(iex.getMessage() + "Domyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3");
        }
        catch(Exception e){
            System.err.println("Nie podano liczby czytelnikow ani pisarzy, omyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3");
        }



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
}