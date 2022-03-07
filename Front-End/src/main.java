
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class main {

    private static HashMap<String, String> userAccountsFromFile = new HashMap<String, String>();
    private static HashMap<String, RentalUnit> rentalUnitsMap = new HashMap<String, RentalUnit>();
    private static ArrayList<RentalUnit> rentalUnits = new ArrayList<>();
    private static ArrayList<User> userAccounts = new ArrayList<>();


    public void writeToTransactionFile(String line, RentalUnit unit, User user, String transactionID){
        try {
            File file = new File("Front-End/resources/transaction_file.txt");
            BufferedWriter bw = null;
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            bw.write(transactionID + "_" + user.toString() + "_" + unit.toString()); // write the transaction

            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean post(String city , float price, int bedrooms, boolean rentFlag){
        return false;
    }
    public static HashMap getUserAccounts(){
        return userAccountsFromFile;
    }
    public static HashMap getRentalUnitsMap(){
        return rentalUnitsMap;
    }
    public static ArrayList getRentalUnitsList(){
        return rentalUnits;
    }
    public static void loadUserAccounts(){ // load user accounts from file and put into hash map
        File file = new File("Front-End/resources/accounts.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] userInfo = line.split("_");
                // USER NAME STORED IN userInfo[0]
                // USER PRIVILEGES STORED IN userInfo[1]
                User u = new User(userInfo[0], userInfo[1]);
                userAccounts.add(u);
                getUserAccounts().put(userInfo[0], userInfo[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadRentalUnits(){
        File file = new File("Front-End/resources/rentalunits.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] userInfo = line.split("_");
                // RENTAL ID IN userInfo[0]
                // OTHER RENTAL INFO userInfo[1 - 6]
                boolean flag = false;
                if(userInfo[5].equals("T")){
                    flag = true;
                }
                RentalUnit newUnit = new RentalUnit(userInfo[0] ,userInfo[1],userInfo[2],Integer.parseInt(userInfo[3])
                                            ,Float.parseFloat(userInfo[4]),flag,Integer.parseInt(userInfo[6]));
                rentalUnits.add(newUnit);
                rentalUnitsMap.put(userInfo[0], newUnit); // store by ID, each containing array thats holds
                                                                    // rental unit data
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void populateFile(File file , ArrayList items){ // populates rentalunits file with any updated units made in transaction
        try {
//            File file = new File("Front-End/resources/rentalunits.txt");
            BufferedWriter bw = null;
            if(!file.exists()){
                file.createNewFile();
            }
            file.delete();
            file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            for(Object u : items){
                bw.write(u.toString()); // write the rental unit details to the file
                bw.write("\n");
            }


            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static boolean login(){  //searches username database, returns true if username found
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter UserName:  ");
        String userName = scan.nextLine();
        if (getUserAccounts().containsKey(userName)){
            System.out.println("User Found, Logging In...");
            return true;
        }
        else{
            System.out.println("User Not Found!");
            String answer= "";

            System.out.println("Would you like to create an account? ");
            answer = scan.nextLine();

            if (answer.equalsIgnoreCase("no")){
                System.out.println("Redirecting...");
                return true;
            }
            else if (answer.equalsIgnoreCase("yes")){
                //create()
                return true;
            }
            return false;
        }

    }

    public static void rent(){  //allows for rent-standard and admin accounts to rent listing
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Rental Unit ID: ");
        String unitID = scan.nextLine();
        System.out.println("Enter # of nights: ");
        String numNights = scan.nextLine();

        if(rentalUnitsMap.containsKey(unitID)){ // if the rental unit exists
            RentalUnit unitToRent = rentalUnitsMap.get(unitID);
            unitToRent.setRentFlag(true);

//            loadRentalUnits();

            populateFile(new File("Front-End/resources/rentalunits.txt"), rentalUnits);
        }
    }
    public static void delete(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter User Name: ");
        String userName = scan.nextLine();

        // delete the user name from the file
        userAccounts.removeIf(i -> i.getUserName().equals(userName));
        populateFile(new File("Front-End/resources/accounts.txt"), userAccounts);

        rentalUnits.removeIf(i -> i.getUserName().equals(userName));

        populateFile(new File("Front-End/resources/rentalunits.txt"), rentalUnits);


    }
    public static void main(String[] args) throws IOException {
        loadUserAccounts();
        loadRentalUnits();

        System.out.println("Hello World!");
        DecimalFormat df = new DecimalFormat("#000000.00");

        System.out.println(df.format(122));
//        User u = new User("NickG" , "FS");
//        Post p = new Post("Toronto" , 99.99f, 4, false, u);
//        System.out.println(p.getRentalUnit().getRentalID());
        login();
        rent();
        delete();
    }


}
