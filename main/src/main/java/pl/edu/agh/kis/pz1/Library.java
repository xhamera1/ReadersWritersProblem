package pl.edu.agh.kis.pz1;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;


public class Library {
    private int readCount = 0;
    private LoggingThread loggingThread;



    private final AtomicBoolean keep_working = new AtomicBoolean(true);

    private final Semaphore semaphoreReaders = new Semaphore(5);
    private final Semaphore semaphoreWriters = new Semaphore(1);

    public Library() {
        loggingThread = new LoggingThread();
        loggingThread.setDaemon(true);
        loggingThread.start();
    }


    public void requestRead(int readerNumber) throws InterruptedException{
        synchronized (this) {
            int temp = semaphoreReaders.getQueueLength() + 1;
            ChronologicalLogger.log("Czytelnik " + readerNumber + " chce wejsc do czytelni. W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                    "   W kolejce do czytelni jest " + temp + " czytelnikow oraz " + semaphoreWriters.getQueueLength() + " pisarzy.");
        }
        semaphoreReaders.acquire();
        synchronized (this) {
            if (readCount == 0) {
                semaphoreWriters.acquire();
            }
            readCount++;
            ChronologicalLogger.log("Czytelnik " + readerNumber + " wchodzi do czytelni i czyta... W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                    "   W kolejce do czytelni jest " + semaphoreReaders.getQueueLength() + " czytelnikow oraz " + semaphoreWriters.getQueueLength() + " pisarzy.");
        }


    }

    public void finishRead(int readerNumber) {
        synchronized (this) {
            readCount--;
            ChronologicalLogger.log("Czytelnik " + readerNumber + " wychodzi z czytelni. W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                    "   W kolejce do czytelni jest " + semaphoreReaders.getQueueLength() + " czytelnikow oraz " + semaphoreWriters.getQueueLength() + " pisarzy.");
            if (readCount == 0) {
                semaphoreWriters.release();
            }
        }
        semaphoreReaders.release();
    }

    public void requestWrite(int writerNumber) throws InterruptedException{
        int temp = semaphoreWriters.getQueueLength() + 1;
        ChronologicalLogger.log("Pisarz " + writerNumber + " chce wejsc do czytelni. W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                "   W kolejce do czytelni jest " + semaphoreReaders.getQueueLength() + " czytelnikow oraz " + temp + " pisarzy.");
        semaphoreWriters.acquire();
        ChronologicalLogger.log("Pisarz " + writerNumber + " wchodzi do czytelni i pisze... W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                "   W kolejce do czytelni jest " + semaphoreReaders.getQueueLength() + " czytelnikow oraz " + semaphoreWriters.getQueueLength() + " pisarzy.");

    }

    public void finishWrite(int writerNumber) {
        ChronologicalLogger.log("Pisarz " + writerNumber + " wychodzi z czytelni. W czytelni jest obecnie " + readCount + " czytelnikow\n" +
                "   W kolejce do czytelni jest " + semaphoreReaders.getQueueLength() + " czytelnikow oraz " + semaphoreWriters.getQueueLength() + " pisarzy.");
        semaphoreWriters.release();
    }

    public void stopThreads(){
        keep_working.set(false);
    }

    public boolean keepWorking(){
        return keep_working.get();
    }



}
