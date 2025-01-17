package pl.edu.agh.kis.pz1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The ChronologicalLogger class provides a centralized logging mechanism that maintains a chronological
 * queue of log records. Logs are stored in a thread-safe queue to enable deferred processing or ordered handling.
 *
 * This class is implemented as a utility class and cannot be instantiated.
 */
public class ChronologicalLogger {
    private static final Logger logger = Logger.getLogger("ChronologicalLogger");
    private static final BlockingQueue<LogRecord> logQueue = new LinkedBlockingQueue<>();
    private static final Handler queueHandler  = new Handler() {

        /**
         * Publishes a log record by adding it to the thread-safe queue.
         * If the queue is full or the addition fails, logs an error to the standard error stream.
         *
         * @param logRecord The log record to be published.
         */
        @Override
        public void publish(LogRecord logRecord) {
            boolean offered = logQueue.offer(logRecord);
            if (!offered) {
                System.err.println("Failed to add log record to the queue: " + logRecord.getMessage());
            }
        }

        // Those methods are required to me overridden:
        /**
         * Flushes the handler. (No operation is performed as this handler does not require flushing.)
         */
        @Override
        public void flush() {
            // No-op: this handler does not require flushing
        }
        /**
         * Closes the handler. (No operation is performed as this handler does not require resource cleanup.)
         */
        @Override
        public void close() throws SecurityException {
            // No-op: no resources to close
        }
    };

    /**
     * Private constructor to prevent instantiation of the utility class.
     * Any attempt to instantiate the class results in an UnsupportedOperationException.
     */
    private ChronologicalLogger(){
        throw new UnsupportedOperationException("This is a utility class and it cannot be initialized");
    }

    static {
        logger.addHandler(queueHandler);
        logger.setUseParentHandlers(false);
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param message The message to be logged.
     */
    public static void log(String message) {
        logger.info(message);
    }


    /**
     * Retrieves the thread-safe queue of log records.
     *
     * @return The blocking queue containing log records.
     */
    public static BlockingQueue<LogRecord> getLogQueue() {
        return logQueue;
    }


}
