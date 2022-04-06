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
    public void loopCoverageZero() throws IOException {
        System.setIn(new ByteArrayInputStream("logout".getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }

    @Test
    public void loopCoverageOnce() throws IOException {
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
    public void loopCoverageTwice() throws IOException {
        System.setIn(new ByteArrayInputStream("login\nKevinCht\nRent\nRNT00000\n2\nlogout".getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter UserName:\r\n" +
                "User Found, Logging In...\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter Rental Unit ID:\r\n" +
                "Enter # of nights:\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }
    @Test
    public void loopCoverageManyTimes() throws IOException {
        System.setIn(new ByteArrayInputStream(("login\nKevinCht\nRent\nRNT00000\n2\npost\nMontreal\n" +
                "99.99\n3\nlogout").getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});

        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter UserName:\r\n" +
                "User Found, Logging In...\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter Rental Unit ID:\r\n" +
                "Enter # of nights:\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter a city:\r\n" +
                "Enter a rental price:\r\n" +
                "Enter the number of bedrooms:\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }

    @Test
    public void loginNoUserNoCreate() throws IOException {

        System.setIn(new ByteArrayInputStream("a\nno\n".getBytes()));

        main.login("accounts.txt");

        assertEquals("Enter UserName:\r\n" +
                "User Not Found!\r\n" +
                "Would you like to create an account?\r\n" +
                "Redirecting...\r\n", testOut.toString());
    }


    @Test
    public void loginNoUserCreateFS() throws IOException {
        System.setIn(new ByteArrayInputStream("a\nyes\nThomasMc\nFS".getBytes()));

        main.login("accounts.txt");

        assertEquals("Enter UserName:\r\n" +
                "User Not Found!\r\n" +
                "Would you like to create an account?\r\n" +
                "Enter username for account:\r\n" +
                "Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: \r\n" +
                "User Created Successfully!\r\n", testOut.toString());
    }

    @Test
    public void loginNoUserCreateRS() throws IOException {
        System.setIn(new ByteArrayInputStream("a\nyes\nThomasMR\nRS".getBytes()));

        main.login("accounts.txt");

        assertEquals("Enter UserName:\r\n" +
                "User Not Found!\r\n" +
                "Would you like to create an account?\r\n" +
                "Enter username for account:\r\n" +
                "Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: \r\n" +
                "User Created Successfully!\r\n", testOut.toString());
    }

    @Test
    public void loginNoUserCreatePS() throws IOException {
        System.setIn(new ByteArrayInputStream("a\nyes\nThomasMP\nPS".getBytes()));

        main.login("accounts.txt");

        assertEquals("Enter UserName:\r\n" +
                "User Not Found!\r\n" +
                "Would you like to create an account?\r\n" +
                "Enter username for account:\r\n" +
                "Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: \r\n" +
                "User Created Successfully!\r\n", testOut.toString());
    }

    @Test
    public void loginYesUser() throws IOException {
        System.setIn(new ByteArrayInputStream("ThomasMc\n".getBytes()));

        main.login("accounts.txt");

        assertEquals("Enter UserName:\r\n" +
                                    "User Found, Logging In...\r\n", testOut.toString());
    }
}