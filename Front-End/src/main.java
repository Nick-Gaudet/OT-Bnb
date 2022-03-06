
import java.util.Scanner;


public class main {
    public boolean post(String cityName , double rentalPrice, double numBedrooms, boolean rented){
        // Handle Constraints
        Scanner scan = new Scanner(System.in);
        if (rentalPrice > 999.99){
            System.out.println("Maximum rental price per night is $999.99!");
            rentalPrice = 999.99;
        }
        else if (cityName.length() > 25){
            System.out.println("City name too long!\nPlease Enter the Another City: ");
            cityName = scan.nextLine();
        }
        else if (numBedrooms > 9){
            System.out.println("Too many bedrooms! Defaulting to 9");
            numBedrooms = 9;
        }
    }
    public static void main(String[] args){
        System.out.println("Hello World!");
    }
}
