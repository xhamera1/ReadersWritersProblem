package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void noExceptionThrown(){
        assertDoesNotThrow(() -> {
            Thread mainThread = new Thread(() -> Main.main(new String[0]));
            mainThread.start();
            mainThread.join();
            mainThread.interrupt();
        }, "No exception should be thrown");
    }


    private String normalizeOutput(String output) {
        return output.replace("\r\n", "\n").trim();
    }

    static Stream<TestCase> provideTestCasesForInvalidArguments() {
        return Stream.of(
                new TestCase(
                        new String[]{"abc", "3"},
                        "Bledny format liczby czytelnikow lub pisarzy\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3\n"
                ),
                new TestCase(
                        new String[]{"5", "3", "extra"},
                        "Bledny format liczby czytelnikow lub pisarzy\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3\n"
                ),
                new TestCase(
                        new String[]{"-1", "-5"},
                        "Musi byc co najmniej jeden pisarz i jeden czytelnik.\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3\n"
                ),
                new TestCase(
                        new String[]{"0", "0"},
                        "Musi byc co najmniej jeden pisarz i jeden czytelnik.\nDomyslne wartosci:\nLiczba czytelnikow: 10\nLiczba pisarzy: 3\n"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForInvalidArguments")
    void testInvalidArguments(TestCase testCase) {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        Main.handleArgs(testCase.args);

        assertEquals(
                normalizeOutput(testCase.expectedError),
                normalizeOutput(errContent.toString())
        );
    }

    private static class TestCase {
        String[] args;
        String expectedError;

        TestCase(String[] args, String expectedError) {
            this.args = args;
            this.expectedError = expectedError;
        }
    }
}