package pl.edu.agh.kis.pz1;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * The LoggingThread class is responsible for processing log records from the ChronologicalLogger's
 * log queue. It continuously retrieves log records from the queue and formats them for output to the console.
 *
 * This class extends Thread and runs as a background daemon thread.
 */
public class LoggingThread extends Thread{
    private final SimpleFormatter formatter = new SimpleFormatter();

    /**
     * The run method retrieves log records from the queue and prints them to the console in a formatted manner.
     * It runs indefinitely until the thread is interrupted.
     */
    @Override
    public void run() {
        try {
            for (;;) {
                LogRecord logRecord = ChronologicalLogger.getLogQueue().take();
                System.out.println(formatter.format(logRecord));
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("LoggingThread interrupted");
        }
    }

}
