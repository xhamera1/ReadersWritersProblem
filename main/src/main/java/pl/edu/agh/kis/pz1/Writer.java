package pl.edu.agh.kis.pz1;

/**
 * The Writer class represents a writer thread.
 * Writers request exclusive access to the library, simulate writing for a fixed duration,
 * and then release the library for other readers or writers.
 */
public class Writer extends Thread {
    private final int number;
    private final Library library;

    /**
     * Constructs a Writer instance with a specific identifier and a reference to the shared library.
     *
     * @param number  The unique identifier for the writer.
     * @param library The shared library instance used by the writer.
     */
    public Writer(int number, Library library) {
        this.number = number;
        this.library = library;
    }


    /**
     * The thread's run method that continuously interacts with the library until it is stopped.
     * The writer:
     * - Requests to write in the library.
     * - Simulates writing by sleeping for 2 seconds.
     * - Finishes writing and releases the library.
     * - Sleeps for 3 seconds before attempting to write again.
     * If the thread is interrupted, it logs the exception, stops the library, and exits gracefully.
     */
    @Override
    public void run() {
        while (library.working()){
            try {
                library.requestWrite(number);
                sleep(2000);
                library.finishWrite(number);
                sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Writer run InterruptedException");
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
