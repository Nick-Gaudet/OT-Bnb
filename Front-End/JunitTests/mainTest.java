import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;

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
        System.setIn(new ByteArrayInputStream("login\nKevinCht\nlogout".getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter UserName:\r\n" +
                "User Found, Logging In...\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }

    @Test
    public void loginNoUserNoCreate() throws IOException {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("a\nno\n".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(ps);

        main.login("accounts.txt");

        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();
        assertEquals(output, "UserName:\r\n" +
                                    "User Not Found!\r\n" +
                                    "Would you like to create an account?\r\n" +
                                    "Redirecting...");
    }

    @Test
    public void loginNoUserYesCreate() throws IOException {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("a\nyes\nThomasMc\nFS".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(ps);

        main.login("accounts.txt");

        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();
        assertEquals(output, "UserName:\r\n" +
                "User Not Found!\r\n" +
                "Would you like to create an account?\r\n" +
                "Enter username for account:\r\n" +
                "Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: \r\n" +
                "User Created Successfully!");
    }
    @Test
    public void loginYesUser() throws IOException {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("ThomasMc\n".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(ps);

        main.login("accounts.txt");

        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();
        assertEquals(output, "UserName:\r\n" +
                                    "User Found, Logging In...");
    }
}