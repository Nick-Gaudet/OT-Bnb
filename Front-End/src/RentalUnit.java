public class RentalUnit {
    private String city;
    private float price;
    private int bedrooms;
    private Boolean rentFlag;
    private String rentalID;

    public RentalUnit(String city, float price, int bedrooms, Boolean rentFlag, String rentalID){
        this.city = city;
        this.price = price;
        this.bedrooms = bedrooms;
        this.rentFlag = rentFlag;
        this.rentalID = rentalID;
    }
    public String getCity(){return city;}
    public float getPrice(){return price;}
    public int getRooms(){return bedrooms;}
    public Boolean getRentFlag(){return rentFlag;}
    public String getRentalID(){return rentalID;}
    public String toString(){return this.rentalID + "/" + this.price + "/" + this.bedrooms + "/" + this.rentFlag + "/"
    + this.rentalID;}
}
