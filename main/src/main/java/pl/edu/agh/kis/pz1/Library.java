package pl.edu.agh.kis.pz1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Library class represents shared resource where multiple threads (readers and writers) interact.
 * It implements a readers-writers synchronization problem, ensuring that:
 * - Multiple readers can access the library concurrently.
 * - Only one writer can access the library at a time, and no readers are allowed during writing.
 * - Proper logging of thread actions is maintained for chronological tracing of events.
 */
public class Library {
    int readCount = 0;
    private LoggingThread loggingThread;

    private final AtomicBoolean working = new AtomicBoolean(true);

    private final Semaphore semaphoreReaders = new Semaphore(5);
    private final Semaphore semaphoreWriters = new Semaphore(1);

    /**
     * Constructor initializes the logging thread to track events and logs them asynchronously.
     * The logging thread is set as a daemon thread and started immediately.
     */
    public Library() {
        loggingThread = new LoggingThread();
        loggingThread.setDaemon(true);
        loggingThread.start();
    }


    /**
     * Method invoked by a reader thread when requesting to enter the library.
     * The method logs the request, ensures that the number of concurrent readers is limited
     * to the semaphore's capacity, and prevents writers from entering while readers are inside.
     *
     * @param readerNumber The unique identifier of the reader thread.
     * @throws InterruptedException If the thread is interrupted while waiting for the semaphore.
     */
    public void requestRead(int readerNumber) throws InterruptedException{
        synchronized (this) {
            int temp = semaphoreReaders.getQueueLength() + 1;
            ChronologicalLogger.log(READER + readerNumber + WANTS_TO_ENTRY + readCount + NUMBER_OF_READERS +  temp + SECOND_NUMBER_OF_READERS + semaphoreWriters.getQueueLength() + NUMBER_OF_WRITERS);
        }
        semaphoreReaders.acquire();
        synchronized (this) {
            if (readCount == 0) {
                semaphoreWriters.acquire();
            }
            readCount++;
            ChronologicalLogger.log(READER + readerNumber + " wchodzi do czytelni i czyta... W czytelni jest obecnie " + readCount + NUMBER_OF_READERS + semaphoreReaders.getQueueLength() +
                    SECOND_NUMBER_OF_READERS + semaphoreWriters.getQueueLength() + NUMBER_OF_WRITERS);
        }


    }

    /**
     * Method invoked by a reader thread when leaving the library.
     * It decrements the count of readers in the library and releases the semaphore.
     * If no readers remain, it releases the writer semaphore.
     *
     * @param readerNumber The unique identifier of the reader thread.
     */
    public void finishRead(int readerNumber) {
        synchronized (this) {
            readCount--;
            ChronologicalLogger.log(READER + readerNumber + " wychodzi z czytelni. W czytelni jest obecnie " + readCount + NUMBER_OF_READERS + semaphoreReaders.getQueueLength()
                    + SECOND_NUMBER_OF_READERS + semaphoreWriters.getQueueLength() + NUMBER_OF_WRITERS);
            if (readCount == 0) {
                semaphoreWriters.release();
            }
        }
        semaphoreReaders.release();
    }

    /**
     * Method invoked by a writer thread when requesting to enter the library.
     * The method ensures exclusive access for the writer, logs the request,
     * and waits for the semaphore if it is currently held by another thread.
     *
     * @param writerNumber The unique identifier of the writer thread.
     * @throws InterruptedException If the thread is interrupted while waiting for the semaphore.
     */
    public void requestWrite(int writerNumber) throws InterruptedException{
        int temp = semaphoreWriters.getQueueLength() + 1;
        ChronologicalLogger.log(WRITER + writerNumber + WANTS_TO_ENTRY + readCount + NUMBER_OF_READERS + semaphoreReaders.getQueueLength() + SECOND_NUMBER_OF_READERS + temp + NUMBER_OF_WRITERS);
        semaphoreWriters.acquire();
        ChronologicalLogger.log(WRITER + writerNumber + " wchodzi do czytelni i pisze... W czytelni jest obecnie " + readCount + NUMBER_OF_READERS + semaphoreReaders.getQueueLength() +
                SECOND_NUMBER_OF_READERS + semaphoreWriters.getQueueLength() + NUMBER_OF_WRITERS);

    }

    /**
     * Method invoked by a writer thread when leaving the library.
     * It logs the action and releases the semaphore, allowing other writers or readers to enter.
     *
     * @param writerNumber The unique identifier of the writer thread.
     */
    public void finishWrite(int writerNumber) {
        ChronologicalLogger.log(WRITER + writerNumber + " wychodzi z czytelni. W czytelni jest obecnie " + readCount + NUMBER_OF_READERS + semaphoreReaders.getQueueLength() +
                SECOND_NUMBER_OF_READERS + semaphoreWriters.getQueueLength() + NUMBER_OF_WRITERS);
        semaphoreWriters.release();
    }

    /**
     * Stops the logging thread and any dependent operations by setting the working flag to false.
     */
    public void stopThreads(){
        working.set(false);
    }

    /**
     * Checks if the library's threads are still operational.
     *
     * @return True if threads are working; otherwise, false.
     */
    public boolean working(){
        return working.get();
    }

    // Constants for logging messages
    private static final String READER = "Czytelnik ";
    private static final String WRITER = "Pisarz ";
    private static final String WANTS_TO_ENTRY = " chce wejsc do czytelni. W czytelni jest obecnie ";
    private static final String NUMBER_OF_READERS = "  czytelnikow\n" +
            "    W kolejce do czytelni jest ";
    private static final String SECOND_NUMBER_OF_READERS = " czytelnikow oraz ";
    private static final String NUMBER_OF_WRITERS = " pisarzy.";



}
