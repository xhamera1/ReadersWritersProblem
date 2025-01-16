package pl.edu.agh.kis.pz1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ChronologicalLogger {
    private static final Logger logger = Logger.getLogger("ChronologicalLogger");
    private static final BlockingQueue<LogRecord> logQueue = new LinkedBlockingQueue<>();
    private static final Handler queueHandler  = new Handler() {
        @Override
        public void publish(LogRecord record) {
            logQueue.offer(record);
        }

        @Override
        public void flush() {}

        @Override
        public void close() throws SecurityException {}
    };

    static {
        logger.addHandler(queueHandler);
        logger.setUseParentHandlers(false);
    }

    public static void log(String message) {
        logger.info(message);
    }

    public static BlockingQueue<LogRecord> getLogQueue() {
        return logQueue;
    }


}
