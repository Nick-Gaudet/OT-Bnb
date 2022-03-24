import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class mainTest {
    public void mainTest() throws IOException{
        main.main(new String[]{"accounts.txt", "rentalunits.txt"});

        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("login\nNickG\nlogout\n".getBytes()));
        System.setIn(stdin);
    }   
}
