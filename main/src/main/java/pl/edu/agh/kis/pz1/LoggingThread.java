package pl.edu.agh.kis.pz1;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LoggingThread extends Thread{
    private final SimpleFormatter formatter = new SimpleFormatter();

    @Override
    public void run() {
        try {
            for (;;) {
                LogRecord record = ChronologicalLogger.getLogQueue().take();
                System.out.println(formatter.format(record));
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("LoggingThread interrupted");
        }
    }

}
