import java.sql.*;

/**
 * Establishes a connection to the database
 * 
 * @author  João Silva 
 * @version 02/02/2023
 */
public class connection {
        // declare a static method named "connection"
        public static void connection() {
        try {
            
            // Load the database driver class, which enables the java application to interact with the database
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Connect to the database using JDBC url, database username and password
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Loop through the SQL warnings if there are any and print the warning details
            for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                System.out.println("SQL Warning:");
                System.out.println("State  : " + warn.getSQLState());
                System.out.println("Message: " + warn.getMessage());
                System.out.println("Error  : " + warn.getErrorCode());
            }

            // Call the logIN_Menu method from the Log_in class and pass the Connection object
            Log_in.logIN_Menu(conn);
        } catch (SQLException se) {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null) {
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        } catch (Exception e) {
            // Catch any other exceptions that may occur and print the exception message
            System.out.println(e);
        }

    }
}