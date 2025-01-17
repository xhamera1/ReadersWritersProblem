package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void testLogic() throws InterruptedException{
        Library library = new Library();
        assertTrue(library.working());
        library.stopThreads();
        assertFalse(library.working());

        assertEquals(0, library.readCount);


        library.requestRead(1);
        assertEquals(1, library.readCount);

        library.finishRead(1);
        assertEquals(0, library.readCount);

        library.requestWrite(1);
        assertEquals(0, library.readCount);

        library.finishWrite(1);
        assertEquals(0, library.readCount);




    }


}