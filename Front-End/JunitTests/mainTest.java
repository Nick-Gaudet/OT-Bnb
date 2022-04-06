import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(testOut));
        System.setIn(systemIn);
    }


    @AfterEach
    public void restoreSystemInputOutput() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    @Test
    public void testCase1() throws IOException {
        System.setIn(new ByteArrayInputStream("login\nNickG\nlogout\n".getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        assertEquals("Welcome to OT-Bnb. Please Login.\n" +
                "Enter UserName:  \n" +
                "User Found, Logging In...\n" +
                "User logged out. Thank you for using OT-Bnb!\n", testOut.toString());
    }
/*
    @Test
    public void test1() throws IOException {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("login\nNickG\n".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(ps);

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();
        assertEquals(output, "User Found, Logging In...");
    }*/

}