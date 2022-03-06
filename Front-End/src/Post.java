import java.util.Scanner;

public class Post {

    String city;
    float price;
    int bedrooms;
    boolean rentFlag;

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
    }

    public void post(){

    }
}
