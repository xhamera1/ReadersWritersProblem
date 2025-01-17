package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggingThreadTest {


    @Test
    void testLogic() throws InterruptedException{
        LoggingThread loggingThread = new LoggingThread();
        loggingThread.start();

        assertTrue(loggingThread.isAlive());

        loggingThread.interrupt();
        loggingThread.join();

        assertFalse(loggingThread.isAlive());




    }

}