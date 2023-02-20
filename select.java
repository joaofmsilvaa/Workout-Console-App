import java.util.Scanner;
import java.sql.*;

// Class to handle the selection of different data to be listed
public class select {

    // String to store the answer of the user
    static String ans;

    // Scanner object to get user input
    static Scanner input = new Scanner(System.in);

    // Method to display the menu for selecting the data to be listed
    public static void selectMenu(Connection conn, int user_id, String name, String Originalpassword) {
        // Flag to determine when to return to the previous menu
        boolean goBackListing = false;
        // Array to store the names of the tables in the database
        String[] databaseFields = new String[4];

        // Initializing the names of the tables
        databaseFields[0] = "utilizador";
        databaseFields[1] = "exercicios";
        databaseFields[2] = "conjunto_exercicios";
        databaseFields[3] = "plano_treino";

        // Displaying the options to the user
        System.out.println("(1) List users           ");
        System.out.println("(2) List Exercises       ");
        System.out.println("(3) List Exercise sets   ");
        System.out.println("(4) List Workout plans   ");
        System.out.println("                         ");
        System.out.println("(E) Exit                 ");

        // Loop to keep displaying the menu until a valid option is selected
        while (!goBackListing) {
            System.out.println("Option:");
            Scanner optionScanner = new Scanner(System.in);
            String option = optionScanner.nextLine();

            // Variable to store the selected table index
            int fieldNum = 0;

            // Check if the user selected to exit
            if (option.equals("E") || option.equals("e")) {
                goBackListing = true;
                System.out.print('\u000C');
                // Call the admin menu again
                Log_in_admin.admin(conn, user_id, name, Originalpassword);
            }

            // Check if the user selected a valid option
            else if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4")) {
                fieldNum = Integer.parseInt(option);

                // Check if the selected option is within the range
                if (fieldNum <= 4 && fieldNum > 0) {
                    goBackListing = true;
                    System.out.print('\u000C');

                    // Call the main method with the selected table
                    main(conn, user_id, name, Originalpassword, databaseFields[fieldNum - 1]);
                } else {
                    System.out.println("The input does not correspond with any valid option");
                }

            } else {
                System.out.println("Invalid option");
            }
        }
    }

    public static void main(Connection conn,int user_id,String name,String Originalpassword, String table) {
        // Call the method to select data from the specified table
        selectData(conn,table);

        // Print instructions for the user to reload the data or exit
        System.out.println("");
        System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");

        // Read the user's answer
        ans = input.nextLine();
        boolean cicle = true;

        // Check if the user's answer is either "r" or "R" or "e" or "E"
        if(ans.equals("r") || ans.equals("R")|| ans.equals("e") || ans.equals("E")){
            
            // While the user's answer is "r" or "R", keep printing the data and asking for input
            while(ans.equals("r") || ans.equals("R")){
                selectData(conn,table);
                System.out.println("");
                System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
                ans = input.nextLine();
            }
            
            // If the user's answer is "e" or "E", clear the screen and call the admin method from Log_in_admin class
            while(ans.equals("e") || ans.equals("E")){
                ans = "";
                System.out.print('\u000C');
                Log_in_admin.admin(conn,user_id,name,Originalpassword);
            }
        }
    }

    public static String[] tablesInfo(String table){
        String[] data = new String[2];

        if(table.equals("utilizador")){
            data[0] = "9"; 
            data[1] = "id_user";

        }
        else if(table.equals("exercicios")){
            data[0] = "6"; 
            data[1] = "id_exercicio";

        }
        else if(table.equals("conjunto_exercicios")){
            data[0] = "5"; 
            data[1] = "id_conjunto";
        }
        else{
            data[0] = "5"; 
            data[1] = "id_treino";
        }

        return data;
    }

    // This method selects data from the specified table and displays it in the console
    public static void selectData(Connection conn, String table)
    {
        try
        {
            // Get the information of the table
            String[] data = tablesInfo(table);

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Clear the console
            System.out.print('\u000C');

            // Create a statement object
            Statement stmt = conn.createStatement();

            // Execute the SELECT statement on the specified table
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+table);

            // Get the number of columns in the result set
            int coluns = Integer.parseInt(data[0]);

            // Loop through the result set and print the values of each row
            while(rs.next()){
                for(int i = 1; i < coluns; i++)
                {
                    System.out.print(rs.getString(i) + "    ");
                }
                System.out.println("");
            }  

            // Close the result set, statement, and the connection
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions and print their information
            while(se != null)
            {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

