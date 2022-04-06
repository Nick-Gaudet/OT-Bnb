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

        assertEquals("Welcome to OT-Bnb. Please Login.\r" +
                "Please enter a command. (Type help for a list of commands): \r" +
                "Enter UserName:  \r" +
                "User Found, Logging In...\r" +
                "Please enter a command. (Type help for a list of commands): \r" +
                "User logged out. Thank you for using OT-Bnb!\r", testOut.toString());
    }
}