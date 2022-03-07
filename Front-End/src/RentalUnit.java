public class RentalUnit {
    private String city;
    private float price;
    private int bedrooms;
    private String rentFlag;
    private String rentalID;
    private String userName;
    private int nightsRemaining;

    //creates RentalUnit class, assigns values
    public RentalUnit(String city, float price, int bedrooms, boolean rentFlag, String rentalID , String userName , int nightsRemaining){
        this.city = city;
        this.price = price;
        this.bedrooms = bedrooms;
        if (rentFlag){
            this.rentFlag = "T";
        }
        else{
            this.rentFlag = "F";
        }
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
    public String toString(){return this.rentalID + "_" + this.userName + "_" + this.city + "_" + this.bedrooms + "_"
    + this.price + "_" + this.rentFlag + "_" + this.nightsRemaining + "\n";}
}
