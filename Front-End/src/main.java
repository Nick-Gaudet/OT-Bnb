
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class main {

    private static HashMap<String, String> userAccountsFromFile = new HashMap<String, String>();
    private static HashMap<String, RentalUnit> rentalUnitsMap = new HashMap<String, RentalUnit>();
    private static ArrayList<RentalUnit> rentalUnits = new ArrayList<>();
    private static ArrayList<User> userAccounts = new ArrayList<>();

    public static void help(){
        System.out.print("List of commands:\n" +
                            "login - Log into your account\n" +
                            "logout - Log out of your account\n" +
                            "post - Post a new rental unit (FS, PS)\n" +
                            "rent - Rent a unit (FS, RS)\n" +
                            "search - Search for an available rental unit\n" +
                            "delete - Delete an existing user (FS)\n" +
                            "create - Create a new user (FS) \n");
    }

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
    public static void post(User u) throws IOException {
        String city,price,bedrooms;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a city: ");
        city = scan.nextLine();

        System.out.println("Enter a rental price: ");
        price = scan.nextLine();

        System.out.println("Enter the number of bedrooms: ");
        bedrooms = scan.nextLine();


        //CONSTRAINTS

        if (Integer.parseInt(price) > 999.99){
            price = "999.99";
            System.out.println("Maximum amount per night is $999.99");
        }

        if (city.length() > 25){
            city = city.substring(0,26);
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
        StringBuilder iD = new StringBuilder(15);
        for (int i = 0; i < 15; i++) { // generate a random alpha numeric ID
            iD.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        //create the rental unit for the post being made
        RentalUnit r = new RentalUnit(iD.toString(),u.getUserName(), city, Integer.parseInt(bedrooms), Float.parseFloat(price), false, 14);
        File file = new File("Front-End/resources/rentalunits.txt");
        BufferedWriter bw = null;
        if(!file.exists()){ // if the file doesnt exist yet
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file,true);
        bw = new BufferedWriter(fw);

        bw.write(r.toString()); // write the rental unit details to the file
        bw.write("\n");

        scan.close();
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
                getUserAccountsMap().put(userInfo[0], userInfo[1]);
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
    public static void populateFile(File file, ArrayList items){ // populates file with any updated units made in transaction
        try {
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
        if (getUserAccountsMap().containsKey(userName)){
            System.out.println("User Found, Logging In...");
            scan.close();
            return true;
        }
        else{
            System.out.println("User Not Found!");
            String answer= "";

            System.out.println("Would you like to create an account? ");
            answer = scan.nextLine();

            if (answer.equalsIgnoreCase("no")){
                System.out.println("Redirecting...");
                scan.close();
                return true;
            }
            else if (answer.equalsIgnoreCase("yes")){
                create();
                scan.close();
                return true;
            }
            scan.close();
            return false;
        }
    }

    public static void logout(User u){  //logs user out
        //TODO: fully implement this
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
        scan.close();
    }

    public static void search(){  //allows for rent-standard and admin accounts to rent listing
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a city: ");
        String cityFilter = scan.nextLine();

        System.out.println("Enter a rental price: ");
        String rentalFilter = scan.nextLine();

        System.out.println("Enter the number of bedrooms: ");
        String numOfBedroomsFilter = scan.nextLine();

        for(int i = 0; i < rentalUnits.size(); i++){
            RentalUnit unit = rentalUnits.get(i);
            if((unit.getCity().equals(cityFilter)) && (unit.getPrice() <= Float.parseFloat(rentalFilter)) && (unit.getRooms() >= Integer.parseInt(numOfBedroomsFilter)) && (unit.getRentFlag().equalsIgnoreCase("F"))){
                System.out.println(unit);
            }
            else{
                System.out.println("Unit Not Found!");
            }
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

        scan.close();
    }
    public static void create(){

        String userType;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username for account: ");
        String user = scan.nextLine();

        if(!doesUsernameExist(user)) {

            while (user.length() > 15) {
                System.out.println("Enter a shorter username: ");
                user = scan.nextLine();
            }

            System.out.println("Enter user type (Full Standard - FS, Rent Standard - RS, Post Standard - PS: ");
            userType = scan.nextLine();

            userAccounts.add(new User(user,userType));
            populateFile(new File("Front-End/resources/accounts.txt"), userAccounts);

            System.out.println("User Created Successfully!");
            scan.close();

        }
        else{
            System.out.println("Username exists!");
            create();

        }

    }
    public static void main(String[] args) throws IOException {
        loadUserAccounts();
        loadRentalUnits();
        Scanner scan = new Scanner(System.in);
        String comm;
        Boolean on = true;
        DecimalFormat df = new DecimalFormat("#000000.00");

        System.out.println(df.format(122));
        User u = new User("NickG" , "FS");
//        Post p = new Post("Toronto" , 99.99f, 4, false, u);
//        System.out.println(p.getRentalUnit().getRentalID());
        while (on){
            System.out.println("Welcome to OT-Bnb. Please enter a command. (Type help for a list of commands)");
            comm = scan.nextLine();
            switch(comm.toLowerCase()){
                case "help":
                help();
                break;

                case "login":
                login();
                break;

                case "logout":
                logout(u);
                on = !on;
                break;

                case "rent":
                rent();
                break;

                case "delete":
                delete();
                break;

                case "post":
                post(u);
                break;

                case "search":
                search();
                break;

                default:
                System.out.println("Invalid command! Type \"help\" for a list of commands.");
            }
        }
        //scan.close();
    }
}