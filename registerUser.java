import java.util.Scanner;
import java.sql.*;

/**
 * Class to register a new user.
 * 
 * @author João Silva 
 * @version 01/02/2023
 */
public class registerUser {

    /**
     * Register a new user
     */
    public static void registerUsernameComplete() {
        try {
            // Check if the user wants to continue or exit
            boolean contOrExit = false;
            contOrExitFunc(contOrExit);

            // Get a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

            // Check if the username is available
            boolean availableName = false;
            String[] userCredentials = register(conn);

            while(availableName == false) {
                String numUser = userCheck(userCredentials[2]);
                if(numUser.equals("0")) {
                    // If the username is available, create the user and call the first menu
                    availableName = true;
                    createUser(userCredentials);
                    int userID = Integer.parseInt(userCredentials[0]);
                    Menus.menu1(conn,userID,userCredentials[2],userCredentials[5],userCredentials[3],userCredentials[4],userCredentials[6]);
                } else {
                    System.out.print('\u000C'); // Clear the console
                    System.out.println("There is already a user with this nickname, try again...");
                    registerUsernameComplete();
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Register main method - Asks the user for a name, e-mail and password and
     * returns the user's credentials to be used in other methods.
     * 
     * @param conn a connection to the database
     * @return an array containing the user's credentials
     **/
    public static String[] register(Connection conn) {
        String[] infos = new String[7];

        // Ask the user for a username, password and other information
        System.out.println("Create an account");
        System.out.println("Username:");
        Scanner usernameScanner = new Scanner(System.in);
        String name = usernameScanner.nextLine();

        String[] goalsAndTraining = goalGetter(false);
        String[] sizes = sizesGetter();

        System.out.println("Password:");
        Scanner passwordScanner = new Scanner(System.in);
        String password = passwordScanner.next();

        // Fill the `infos` array with the user's information
        infos[0] = idGetter(conn,"id_user","utilizador");
        infos[1] = goalsAndTraining[1];
        infos[2] = name;
        infos[3] = sizes[0];
        infos[4] = sizes[1];
        infos[5] = goalsAndTraining[0];
        infos[6] = password;

        return infos;
    }

    public static String idGetter(Connection conn, String column, String table) {
        int maxID = 0;
        String admStatus = "0";
        String id = "";

        try {
            // create a statement object from the connection object
            Statement stmt = conn.createStatement();
            // execute the query to retrieve the maximum value of the given column in the given table
            ResultSet rs = stmt.executeQuery("SELECT MAX(" + column + ") from " + table + "");

            // increment the maximum value by 1 to get the next id
            while (rs.next()) {
                maxID = rs.getInt(1) + 1;
            }

            // convert the integer maxID to string
            String maxIDstr = Integer.toString(maxID);

            // set the id as the string representation of maxID
            id = maxIDstr;
        } catch (SQLException se) {
            // print a message indicating that an SQL exception has occurred
            System.out.println("SQL Exception:");

            // loop through all the exceptions in the chain and print their information
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                // move on to the next exception in the chain
                se = se.getNextException();
            }
        }

        // return the id
        return id;
    }

    public static String[] sizesGetter(){
        // controls the main loop
        boolean sizesCicle = true;
        // controls the weight input loop
        boolean weightCicle = true;
        // controls the height input loop
        boolean heightCicle;

        // array to store the sizes
        String[] sizes = new String[2]; 

        while(sizesCicle){ // main loop

            while(weightCicle){ // weight input loop

                // Asks for the weight
                System.out.println("Weight:");
                Scanner weightScanner = new Scanner(System.in);
                String weight = weightScanner.next();

                // validate the input if it is a valid double
                weight = validDouble(weight);

                // if the input is not a valid double
                if(weight.equals("error")){
                    // clear the console
                    System.out.print('\u000C');
                    System.out.println("Invalid input");
                    System.out.println("The given information dosen't match the expected data type");
                    System.out.println("Try inserting a valid number");
                    System.out.println("An example for the expected input is: 60");
                    System.out.println("Or: 60.20");
                    System.out.println();
                }
                else{ // if the input is a valid double
                    // exit the weight input loop
                    weightCicle = false;
                    // store the weight
                    sizes[0] = weight;
                }
            }

            // start the height input loop
            heightCicle = true;
            while(heightCicle){
                // Asks for the height
                System.out.println("height:");
                Scanner heightScanner = new Scanner(System.in);
                String height = heightScanner.next();

                // validate the input if it is a valid double
                height = validDouble(height);

                // if the input is not a valid double
                if(height.equals("error")){
                    // clear the console
                    System.out.print('\u000C');
                    System.out.println("Invalid input");
                    System.out.println("The given information dosen't match the expected data type");
                    System.out.println("Try inserting a valid number");
                    System.out.println("An example for the expected input is: 1.70");
                    System.out.println();
                }

                else{ // if the input is a valid double

                    // exit the height input loop
                    heightCicle = false;
                    // exit the main loop
                    sizesCicle = false;
                    // store the height
                    sizes[1] = height;

                }
            }
        }

        // return the sizes
        return sizes;
    }

    public static String[] goalGetter(boolean goalOption){
        String[] goalAndTraining = new String[2];

        // Loop until the user enters a valid input
        while(goalOption == false){

            // Ask for the goal
            System.out.println("If you want to lose weight click \"1\" if you want to get in shape click \"2\":");
            Scanner goalScanner = new Scanner(System.in);
            int goalValue = goalScanner.nextInt();

            // Check the entered value
            if(goalValue == 1){
                goalAndTraining[0] = "Perder peso";
                goalOption = true;
                goalAndTraining[1] = "1";
            }

            else if(goalValue == 2){
                goalAndTraining[0] = "ganhar massa";
                goalOption = true;
                goalAndTraining[1] = "2";
            }

            // If the entered value is incorrect
            else{
                System.out.println("Incorrect Option, try again:");

            }
        }

        // Return the goal and the corresponding value
        return goalAndTraining;
    }

    /**
     * A method to validate if a string can be parsed to a double value
     * @param input - the input string to be validated
     * @return - the input string if it can be parsed to double, or "error" if the input is invalid
     */
    public static String validDouble(String input) {
        boolean cicle = true;

        // Continue the loop until a valid double value is obtained or "error" is returned
        while (cicle) {
            try {
                Double data = Double.parseDouble(input);

                // Check if the input string contains a comma and replace it with a dot
                for (int index = 0; index < input.length(); index++) {
                    char dotFind = input.charAt(index);

                    if (dotFind == ',') {
                        input = input.substring(0, index) + "." + input.substring(input.length());
                    }
                }

            } catch (Exception error) {
                // Return "error" if the input string cannot be parsed to double
                input = "error";

            }
            cicle = false;
        }

        return input;
    }

    public static void contOrExitFunc(boolean contOrExit){
        // Loops until the "contOrExit" boolean is set to true
        while(!contOrExit){

            // Prompts user to either exit or continue
            System.out.println("If you wish to go back type \"exit\" if not type \"continue\":");

            // Scans user input
            Scanner exitScan = new Scanner(System.in);
            String exitOption = exitScan.nextLine();

            // Check if user input is "exit"
            if(exitOption.equals("exit")){
                // Clears the console
                System.out.print('\u000C');
                // Sets "contOrExit" to true, breaking the loop
                contOrExit = true;
                // Calls the "connection" function
                connection.connection();
            }

            // Check if user input is "continue"
            else if(exitOption.equals("continue")){
                // Clears the console
                System.out.print('\u000C');
                // Sets "contOrExit" to true, breaking the loop
                contOrExit = true;
            }

            // If user input is not "exit" or "continue"
            else{
                // Clears the console
                System.out.print('\u000C');
                // Prints an error message
                System.out.println("Invalid input");
                System.out.println("Please insert \"exit\" or \"continue\"");
                System.out.println();
            }
        }
    }

    public static String userCheck(String info) {
        String userAmmount = null;

        try {
            
            // Get a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

            
            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Counts the amount of accounts with a name equal to the given by the user
            ResultSet userQuerry = stmt.executeQuery("SELECT COUNT(*) FROM utilizador WHERE utilizador.nome = '" + info + "'");

            stmt = conn.createStatement();
            while (userQuerry.next()) {

                for (int i = 1; i < 8; i++) {
                    // Get the count of accounts with the same name
                    userAmmount = userQuerry.getString(1);
                }
            }

            // Close the result set, statement and the connection
            userQuerry.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }

        return userAmmount;
    }

    // This function creates a new user in the database
    public static void createUser(String[] infos){
        try{
            // Establish a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );    

            // Get the max id from the database
            int maxID = Integer.parseInt(infos[0]);

            // Set the default admin status to 0
            String admStatus = "0";

            // Create a prepared statement to insert the user into the database
            PreparedStatement prepared_statement = conn.prepareStatement("INSERT INTO utilizador VALUES(?,?,?,?,?,?,?,?)");
            prepared_statement.setInt(1, maxID);
            prepared_statement.setString(2, infos[1]);
            prepared_statement.setString(3, infos[2]);
            prepared_statement.setString(4, infos[3]);
            prepared_statement.setString(5, infos[4]);
            prepared_statement.setString(6, infos[5]);
            prepared_statement.setString(7, infos[6]);
            prepared_statement.setString(8, admStatus);

            // Execute the update statement and store the result
            int result_set = prepared_statement.executeUpdate();

            // Close the database connection
            conn.close();

        }
        catch( SQLException se ){
            System.out.println( "SQL Exception:" ) ;

            // Loop through the SQL Exceptions
            while( se != null )
            {
                System.out.println( "State  : " + se.getSQLState()  ) ;
                System.out.println( "Message: " + se.getMessage()   ) ;
                System.out.println( "Error  : " + se.getErrorCode() ) ;

                se = se.getNextException() ;
            }

        }
    }
}
