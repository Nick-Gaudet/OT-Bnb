
import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class main {

    private static HashMap<String, String> userAccountsFromFile = new HashMap<String, String>();


    public boolean post(String city , float price, int bedrooms, boolean rentFlag){
        return false;
    }
    public static HashMap getUserAccounts(){
        return userAccountsFromFile;
    }
    public static void loadUserAccounts(){ // load user accounts from file and put into hash map
        File file = new File("Front-End/resources/accounts.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String [] userInfo = line.split("_");
                // USER NAME STORED IN userInfo[0]
                // USER PRIVILEGES STORED IN userInfo[1]
                getUserAccounts().put(userInfo[0], userInfo[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    }
    public static void main(String[] args) throws IOException {
        loadUserAccounts();
        System.out.println("Hello World!");
        DecimalFormat df = new DecimalFormat("#000000.00");

        System.out.println(df.format(122));
        Post p = new Post("Toronto" , 99.99f, 4, false);
        System.out.println(p.getRentalUnit().getRentalID());
        login();
    }


}
