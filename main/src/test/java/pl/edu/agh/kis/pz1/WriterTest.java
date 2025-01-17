package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

    @Test
    void testConstructor(){
        Library library1 = new Library();
        Library library2 = new Library();
        Writer writer1 = new Writer(1, library1);

        assertEquals(1, writer1.getNumber());
        assertEquals(writer1.getLibrary(), library1);

        assertNotEquals(2, writer1.getNumber());
        assertNotEquals(writer1.getLibrary(), library2);

    }

    @Test
    void writerAlive() throws InterruptedException{
        Library library1 = new Library();
        Writer writer = new Writer(1, library1);

        writer.start();

        assertTrue(writer.isAlive());

        writer.interrupt();
        writer.join();

        assertFalse(writer.isAlive());

    }

    @Test
    void manyThreadsWriters() throws InterruptedException{
        Library library1 = new Library();
        Writer writer1 = new Writer(1, library1);
        Writer writer2 = new Writer(2, library1);
        Writer writer3 = new Writer(3, library1);
        Writer writer4 = new Writer(4, library1);

        writer1.start();
        writer2.start();
        writer3.start();
        writer4.start();

        assertTrue(writer1.isAlive());
        assertTrue(writer2.isAlive());
        assertTrue(writer3.isAlive());
        assertTrue(writer4.isAlive());

        writer1.interrupt();
        writer2.interrupt();
        writer3.interrupt();
        writer4.interrupt();

        writer1.join();
        writer2.join();
        writer3.join();
        writer4.join();

        assertFalse(writer1.isAlive());
        assertFalse(writer2.isAlive());
        assertFalse(writer3.isAlive());
        assertFalse(writer4.isAlive());

    }



}