package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChronologicalLoggerTest {

    @Test
    void testLogic(){
        String message = "Wiadomosc testowa 123";
        assertTrue(ChronologicalLogger.getLogQueue().isEmpty());

        ChronologicalLogger.log(message);

        assertFalse(ChronologicalLogger.getLogQueue().isEmpty());



    }

}