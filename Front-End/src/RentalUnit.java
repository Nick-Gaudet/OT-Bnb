import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RentalUnit {
    private String city;
    private float price;
    private int bedrooms;
    private String rentFlag;
    private String rentalID;
    private String userName;
    private int nightsRemaining;

    //creates RentalUnit class, assigns values
    public RentalUnit(String rentalID, String userName, String city, int bedrooms, float price, boolean rentFlag, int nightsRemaining){
        this.city = city;
        this.price = price;
        this.bedrooms = bedrooms;
        setRentFlag(rentFlag);
        this.rentalID = rentalID;
        this.userName = userName;
        this.nightsRemaining = nightsRemaining;
    }
    //getter and setters for each variable in class
    public String getCity(){return city;}
    public float getPrice(){return price;}
    public int getRooms(){return bedrooms;}
    public String getRentFlag(){return rentFlag;}
    public String getRentalID(){return rentalID;}
    public String getUserName(){return userName;}

    public void setRentFlag(boolean rentFlag){
        if (rentFlag){
            this.rentFlag = "T";
        }
        else{
            this.rentFlag = "F";
        }
    }
    public String toString(){return this.rentalID + "_" + this.userName + "_" + this.city + "_" + this.bedrooms + "_"
    + this.price + "_" + this.rentFlag + "_" + this.nightsRemaining;}

    public void storeRental(RentalUnit r) throws IOException {
        //create a new rental unit to store

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder iD = new StringBuilder(15);
        for (int i = 0; i < 15; i++) { // generate a random alpha numeric ID
            iD.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }


        File file = new File("Front-End/resources/rentalunits.txt");
        BufferedWriter bw = null;
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);

        bw.write(r.toString()); // write the rental unit details to the file
        bw.write("\n");

        bw.close();
    }
}
