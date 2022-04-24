import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
//If tests do not work, try them individually, using the working directory
// (path to OT-Bnb folder)/Front-End/src
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
/*
* Loop coverage, enters main loop once, twice, and many times by calling
* functions an appropriate amount of times
* */
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
                "99.99\n3\ncreate\nJoshL\nPS\ndelete\nlogout").getBytes()));

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
                "Enter username for account:\r\n" +
                "Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: \r\n"+
                "User Created Successfully!\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "You don't have privileges!\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }

    /*
    * These tests are decision coverage for login(), testing the following decisions:
    * is the user found in the accounts file
    * if not, does the user want to create an account
    * if so, which type of account should be created
    * */
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

    /*STATEMENT COVERAGE FOR POST
    FULL COVERAGE (+ constraints) = 27
    21 with no constraints(partial coverage)
    21 + 2 * constraints (i.e 23 reached with 1 constraint)
    MIN expected 21, at MAX 27
    * */
    @Test
    public void partialStatementCoverage() throws IOException{
        System.setIn(new ByteArrayInputStream(("login\nNickG\npost\nWhitby\n150.50\n3\nlogout").getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});


        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter UserName:\r\n" +
                "User Found, Logging In...\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter a city:\r\n" +
                "Enter a rental price:\r\n" +
                "Enter the number of bedrooms:\r\n" +
                "Statements:21\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }

    @Test
    public void fullStatementCoverage() throws IOException{
        System.setIn(new ByteArrayInputStream(("login\nNickG\npost\nWhitbyButItIsLongerThanTheCity\n" +
                "1050.50\n30\nlogout").getBytes()));

        main.main(new String[]{"accounts.txt", "transactions.txt"});


        assertEquals("Welcome to OT-Bnb. Please Login.\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter UserName:\r\n" +
                "User Found, Logging In...\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "Enter a city:\r\n" +
                "Enter a rental price:\r\n" +
                "Enter the number of bedrooms:\r\n" +
                "Maximum amount per night is $999.99\r\n"+
                "City name too long! Shortening to WhitbyButItIsLo\r\n" +
                "Max number of bedrooms is 9! Defaulting...\r\n"+
                "Statements:27\r\n" +
                "Please enter a command. (Type help for a list of commands):\r\n" +
                "User logged out. Thank you for using OT-Bnb!\r\n", testOut.toString());
    }
}