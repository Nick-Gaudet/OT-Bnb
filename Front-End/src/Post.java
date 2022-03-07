import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class handles each listing in the OT-Bnb system.
 * There are safeguards for price limits, city name length,
 * and bedroom amount.
 * This class should output an alphanumeric ID to a text file,
 * leaving it for future manipulation.
 */
public class Post {

    private String city;
    private float price;
    private int bedrooms;
    private boolean rentFlag;
    private RentalUnit newUnit;

    public Post(String city , float price , int bedrooms, boolean rentFlag) throws IOException {
        // Handle Constraints
        Scanner scan = new Scanner(System.in);
        if (price > 999.99){
            System.out.println("Maximum rental price per night is $999.99!");
            price = 999.99f;
        }
        else if (city.length() > 25){
            System.out.println("City name too long!\nPlease Enter the Another City: ");
            city = scan.nextLine();
        }
        else if (bedrooms > 9){
            System.out.println("Too many bedrooms! Defaulting to 9");
            bedrooms = 9;
        }
        this.bedrooms = bedrooms;
        this.city =city;
        this.price = price;
        this.rentFlag = rentFlag;

        //create a new rental unit to store

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder iD = new StringBuilder(15);
        for (int i = 0; i < 15; i++) { // generate a random alpha numeric ID
            iD.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        this.newUnit = new RentalUnit(city,price,bedrooms,rentFlag,iD.toString());

        File file = new File("Front-End/resources/rentalunits.txt");
        BufferedWriter bw = null;
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);

        bw.write(this.newUnit.toString()); // write the rental unit details to the file

        bw.close();


    }
    public RentalUnit getRentalUnit(){return this.newUnit;}
}
