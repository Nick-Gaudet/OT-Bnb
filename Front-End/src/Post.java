import java.util.Random;
import java.util.Scanner;

public class Post {

    private String city;
    private float price;
    private int bedrooms;
    private boolean rentFlag;
    private RentalUnit newUnit;

    public Post(String city , float price , int bedrooms, boolean rentFlag){
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
    }
    public RentalUnit getRentalUnit(){return this.newUnit;}
}
