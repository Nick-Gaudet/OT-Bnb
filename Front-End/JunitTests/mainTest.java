import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {

    @Test
    void login(){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            main.login();
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format(
                        "Enter UserName:  " +
                                "User Found, Logging In..."),
                bytes.toString());
    }

}