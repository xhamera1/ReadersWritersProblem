package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    void testConstructor(){
        Library library1 = new Library();
        Library library2 = new Library();
        Reader reader1 = new Reader(1, library1);

        assertEquals(1, reader1.getNumber());
        assertEquals(reader1.getLibrary(), library1);

        assertNotEquals(2, reader1.getNumber());
        assertNotEquals(reader1.getLibrary(), library2);

    }

    @Test
    void readerAlive() throws InterruptedException{
        Library library1 = new Library();
        Reader reader = new Reader(1, library1);

        reader.start();

        assertTrue(reader.isAlive());

        reader.interrupt();
        reader.join();

        assertFalse(reader.isAlive());

    }

    @Test
    void manyThreadsReaders() throws InterruptedException{
        Library library1 = new Library();
        Reader reader1 = new Reader(1, library1);
        Reader reader2 = new Reader(2, library1);
        Reader reader3 = new Reader(3, library1);
        Reader reader4 = new Reader(4, library1);

        reader1.start();
        reader2.start();
        reader3.start();
        reader4.start();

        assertTrue(reader1.isAlive());
        assertTrue(reader2.isAlive());
        assertTrue(reader3.isAlive());
        assertTrue(reader4.isAlive());

        reader1.interrupt();
        reader2.interrupt();
        reader3.interrupt();
        reader4.interrupt();

        reader1.join();
        reader2.join();
        reader3.join();
        reader4.join();

        assertFalse(reader1.isAlive());
        assertFalse(reader2.isAlive());
        assertFalse(reader3.isAlive());
        assertFalse(reader4.isAlive());

    }




}