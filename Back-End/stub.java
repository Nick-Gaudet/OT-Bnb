import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.StringBuilder;


public class stub {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("../Front-End/inputs/mergedTransactions.txt"));
        try(PrintWriter writer = new PrintWriter("transactionTable.csv")){
            StringBuilder sb = new StringBuilder();
            while(line != null){
                String line = br.readLine();
                sb.append(line.substring(0,3));
                sb.append(",");
                sb.append(line.substring(4,12));
                sb.append(",");
                sb.append(line.substring(13,15));
                sb.append(",");
                sb.append(line.substring(16,25));
                sb.append(",");
                sb.append(line.substring(26,40));
                sb.append(",");
                sb.append(line.charAt(42));
                sb.append(",");
                sb.append(line.substring(43,52));
                sb.append(",");
                sb.append(line.substring(54,56));
                sb.append("\n");
                writer.write(sb.toString());
            }catch{
                System.out.println("Cannot write to file");
            }
        }
    }
}