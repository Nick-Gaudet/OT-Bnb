
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class main {
    //TODO: get file path to be Front-End/resources/ + args[n...]  for command line input on program load

    //GLOBALS
    private static HashMap<String, String> userAccountsFromFile = new HashMap<String, String>();
    private static HashMap<String, RentalUnit> rentalUnitsMap = new HashMap<String, RentalUnit>();
    private static ArrayList<RentalUnit> rentalUnits = new ArrayList<RentalUnit>();
    private static ArrayList<User> userAccounts = new ArrayList<User>();
    private static ArrayList<String> transactions = new ArrayList<String>();
    private static User currentUser;
    private static RentalUnit rentalUnitForTransactionInfo;
    private static Scanner scan = new Scanner(System.in);

    public static void help(){
        System.out.print("List of commands:\n" +
                            "logout - Log out of your account\n" +
                            "post - Post a new rental unit (FS, PS)\n" +
                            "rent - Rent a unit (FS, RS)\n" +
                            "search - Search for an available rental unit\n" +
                            "delete - Delete an existing user (FS)\n" +
                            "create - Create a new user (FS) \n");
    }
    public static String makeTransactionString(String transCode, RentalUnit unit, User user){
        DecimalFormat df = new DecimalFormat("#000000.00");
        return String.format("%s %-8s %s %-8s %-15s %s %s %02d\n", transCode, user.getUserName(), user.getPrivileges(),
                unit.getRentalID(), unit.getCity(), unit.getRooms(), df.format(unit.getPrice()),unit.getNightsRemaining());
    }
    public static void writeToTransactionFile(){
        try {
            int numFiles = new File("outputs/").list().length; // gets number of trans files, makes a new
                                                                // file with proper number
            File file = new File("outputs/transaction_"+ numFiles +".txt");
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
            for(String s : transactions){
                bw.write(s); // write the transaction
            }

            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void post() throws IOException {
        String city,price,bedrooms;
        System.out.println("Enter a city: ");
        city = scan.nextLine();

        System.out.println("Enter a rental price: ");
        price = scan.nextLine();

        System.out.println("Enter the number of bedrooms: ");
        bedrooms = scan.nextLine();


        //CONSTRAINTS

        if (Float.parseFloat(price) > 999.99){
            price = "999.99";
            System.out.println("Maximum amount per night is $999.99");
        }

        if (city.length() > 15){
            city = city.substring(0,15);
            System.out.println("City name too long! Shortening to " + city);
        }

        if (Integer.parseInt(bedrooms) > 9){
            bedrooms = "9";
            System.out.println("Max number of bedrooms is 9! Defaulting...");
        }

        //create a new rental unit to store

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        //generate a random unit ID
        StringBuilder iD = new StringBuilder(8);
        for (int i = 0; i < 8; i++) { // generate a random alpha numeric ID
            iD.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        //create the rental unit for the post being made
        RentalUnit r = new RentalUnit(iD.toString(),currentUser.getUserName(), city, Integer.parseInt(bedrooms), Float.parseFloat(price), false, 14);
        rentalUnitForTransactionInfo = r;

        File file = new File("resources/rentalunits.txt");
        BufferedWriter bw = null;
        if(!file.exists()){ // if the file doesnt exist yet
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsolutePath(),true);
        bw = new BufferedWriter(fw);

        bw.write(r.toString()); // write the rental unit details to the file
        bw.write("\n");

        bw.close();

    }
    public static HashMap getUserAccountsMap(){
        return userAccountsFromFile;
    }
    public static HashMap getRentalUnitsMap(){
        return rentalUnitsMap;
    }
    public static ArrayList getRentalUnitsList(){
        return rentalUnits;
    }

    public static void loadUserAccounts(String f){ // loads existing user accounts from file on Ot-Bnb launch then maps them
        File file = new File("./resources/" + f);

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] userInfo = line.split("_");
                if (userInfo.length == 1){
                    break;
                }
                // USER NAME STORED IN userInfo[0]
                // USER PRIVILEGES STORED IN userInfo[1]
                User u = new User(userInfo[0].trim(), userInfo[1]);
                userAccounts.add(u);
                userAccountsFromFile.put(userInfo[0].trim(), userInfo[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRentalUnits(String f){ // loads existing rental units from file on Ot-Bnb launch then maps them
        File file = new File("./resources/" + f);
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] userInfo = line.split("_");
                // RENTAL ID IN userInfo[0]
                // OTHER RENTAL INFO userInfo[1 - 6]
                if (userInfo.length == 1){
                    break;
                }
                boolean flag = false;
                if(userInfo[5].equals("T")){
                    flag = true;
                }
                RentalUnit newUnit = new RentalUnit(userInfo[0].trim() ,userInfo[1].trim(),userInfo[2].trim(),Integer.parseInt(userInfo[3])
                                            ,Float.parseFloat(userInfo[4]),flag,Integer.parseInt(userInfo[6]));

                rentalUnits.add(newUnit);
                rentalUnitsMap.put(userInfo[0].trim(), newUnit); // store by ID, each containing array thats holds
                                                                    // rental unit data
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void populateFile(File file, ArrayList items){ // populates file with any updated units made in transaction
        try {
            BufferedWriter bw = null;
            if(!file.exists()){
                file.createNewFile();
            }
            file.delete();
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
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
        System.out.println("Enter UserName:  ");
        String userName = scan.nextLine();
        if (getUserAccountsMap().containsKey(userName)){
            System.out.println("User Found, Logging In...");
            currentUser = new User(userName, (String) getUserAccountsMap().get(userName)); // set the current user
            return true;
        }
        else{
            System.out.println("User Not Found!");
            String answer= "";

            System.out.println("Would you like to create an account? ");
            answer = scan.nextLine();

            if (answer.equalsIgnoreCase("no")){
                System.out.println("Redirecting...");
                return false;
            }
            else if (answer.equalsIgnoreCase("yes")){
                create();
                return true;
            }
            return false;
        }
    }

    public static void logout(User u){  //logs user out
        System.out.println("User logged out. Thank you for using OT-Bnb!");
    }

    public static Boolean doesUsernameExist(String name){

        for(User i : userAccounts){ // search all user accounts
            if (name.equals(i.getUserName())){
                return true;
            }
        }
        return false;
    }
    public static void rent(){  //allows for rent-standard and admin accounts to rent listing
        System.out.println("Enter Rental Unit ID: ");
        String unitID = scan.nextLine();
        System.out.println("Enter # of nights: ");
        String numNights = scan.nextLine();

        if(rentalUnitsMap.containsKey(unitID)){ // if the rental unit exist
            RentalUnit unitToRent = rentalUnitsMap.get(unitID);
            unitToRent.setRentFlag(true);
            populateFile(new File("resources/rentalunits.txt"), rentalUnits);
            transactions.add(makeTransactionString("05",unitToRent,currentUser));
        }
    }

    public static void search(){  //allows for rent-standard and admin accounts to rent listing
        System.out.println("Enter a city: ");
        String cityFilter = scan.nextLine();

        System.out.println("Enter a rental price: ");
        String rentalFilter = scan.nextLine();

        System.out.println("Enter the number of bedrooms: ");
        String numOfBedroomsFilter = scan.nextLine();
        for(int i = 0; i < rentalUnits.size(); i++){
            RentalUnit unit = rentalUnits.get(i);
            if((unit.getCity().equalsIgnoreCase(cityFilter)) && (unit.getPrice() <= Float.parseFloat(rentalFilter)) && (unit.getRooms() <= Integer.parseInt(numOfBedroomsFilter)) && (unit.getRentFlag().equalsIgnoreCase("F"))){
                System.out.println(unit);
            }
        }
    }

    public static void delete(){ // deletes the user from file and any units correlated, updates files as well
        System.out.println("Enter User Name: ");
        String userName = scan.nextLine();

        // delete the user name from the file
        userAccounts.removeIf(i -> i.getUserName().equals(userName));
        populateFile(new File("resources/accounts.txt"), userAccounts);

        rentalUnits.removeIf(i -> i.getUserName().equals(userName));
        populateFile(new File("resources/rentalunits.txt"), rentalUnits);

    }
    public static void create(){ // creates a new user account, updates the file as well

        String userType;
        System.out.println("Enter username for account: ");
        String user = scan.nextLine();

        if(!doesUsernameExist(user)) {

            while (user.length() > 10) {
                System.out.println("Enter a shorter username: ");
                user = scan.nextLine();
            }

            System.out.println("Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: ");
            userType = scan.nextLine();


            userAccounts.add(new User(user,userType));
            populateFile(new File("resources/accounts.txt"), userAccounts);
            currentUser = new User(user, userType);

            System.out.println("User Created Successfully!");


        }
        else{
            System.out.println("Username already exists!");
        }

    }
    public static void main(String[] args) throws IOException {
        String userAccountsFile = args[0];
        String rentalUnitsFile = args[1];
        loadUserAccounts(userAccountsFile);
        loadRentalUnits(rentalUnitsFile);
        String comm;
        String transCode;
        Boolean on = true;
        Boolean loggedin = false;

        System.out.println("Welcome to OT-Bnb. Please Login.");/*
        if(!login()){
            System.out.println("Unable to Login! You can not use OT-BnB without having an account! Exiting...");
            System.exit(1);
        }*/

        //by this point currentUser is set for the current transactions
        while (on){
            System.out.println("Please enter a command. (Type help for a list of commands): ");
            comm = scan.nextLine();
            switch(comm.toLowerCase()){
                case "login":
                    if(loggedin){
                        System.out.println("There is already a user logged in!");
                        break;
                    }
                    if(!login()){
                        System.out.println("Unable to Login! You can not use OT-BnB without having an account! Exiting...");
                        System.exit(1);
                    }
                    loggedin = true;
                    break;

                case "create":

                    if(loggedin){
                        if(currentUser.getPrivileges().equals("FS")){
                            transCode = "01";
                            create();
                            transactions.add(makeTransactionString(transCode,new RentalUnit(),currentUser));
                        }
                        else{
                            System.out.println("You don't have privileges!");
                        }
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                case "delete":

                    if(loggedin){
                        if(currentUser.getPrivileges().equals("FS")){
                            transCode = "02";
                            delete();
                            transactions.add(makeTransactionString(transCode,new RentalUnit(),currentUser));
                        }
                        else{
                            System.out.println("You don't have privileges!");
                        }
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                case "post":

                    if(loggedin){
                        if(!currentUser.getPrivileges().equals("RS")){
                            transCode = "03";
                            post();
                            transactions.add(makeTransactionString(transCode,rentalUnitForTransactionInfo,currentUser));
                        }
                        else{
                            System.out.println("Only post-standard or full-standard(Admin) are allowed to post!");
                        }
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                case "search":
                    
                    if(loggedin){
                        transCode = "04";
                        search();
                        transactions.add(makeTransactionString(transCode,new RentalUnit(),currentUser));
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                case "rent":

                    if(loggedin){
                        if(!currentUser.getPrivileges().equals("PS")) {
                            transCode = "05";
                            rent();
                            //transaction added in rent function
                        }
                        else{
                            System.out.println("Only rent-standard or full-standard(Admin) are allowed to rent!");
                        }
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                case "help":
                    help();
                    break;

                case "logout":
                    if (loggedin){
                        logout(currentUser);
                        loggedin = false;
                        on = !on;
                    }
                    else{
                        System.out.println("Only logged-in users can perform this task.");
                    }
                    break;

                default:
                System.out.println("Invalid command! Type \"help\" for a list of commands.");
            }
        }
        transCode = "00";
        transactions.add(makeTransactionString(transCode,new RentalUnit(),currentUser));
        writeToTransactionFile();
    }
}