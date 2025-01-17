package pl.edu.agh.kis.pz1;


/**
 * The Reader class represents a reader thread that interacts with the {@link Library}.
 * Readers request shared access to the library, simulate reading for a fixed duration,
 * and then release the library for other readers or writers.
 */
public class Reader extends Thread{
    private final int number;
    private final Library library;

    /**
     * Constructs a Reader instance with a specific identifier and a reference to the shared library.
     *
     * @param number  The unique identifier for the reader.
     * @param library The shared library instance used by the reader.
     */
    public Reader(int number, Library library){
        this.number = number;
        this.library = library;
    }


    /**
     * The thread's run method that continuously interacts with the library until it is stopped.
     * The reader:
     * - Requests to read from the library.
     * - Simulates reading by sleeping for 1.5 seconds.
     * - Finishes reading and releases the library.
     * - Sleeps for 2 seconds before attempting to read again.
     * If the thread is interrupted, it logs the exception, stops the library, and exits gracefully.
     */
    @Override
    public void run(){
        while (library.working()){
            try {
                library.requestRead(number);
                sleep(1500);
                library.finishRead(number);
                sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Reader run InterruptedException");
                Thread.currentThread().interrupt();
                library.stopThreads();
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public Library getLibrary() {
        return library;
    }
}
