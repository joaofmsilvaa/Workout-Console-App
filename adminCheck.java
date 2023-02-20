import java.sql.*;
public class adminCheck {
    /**
     * This method verifies if the input name and password match the name and password of an admin user in the database.
     * 
     * @param the name and password of the user to be verified 
     * 
     * @return an integer indicating if the user is an admin: 
     * returns 1 if the user is an admin, 
     * returns 0 otherwise.
     */
    public static int querry(String name, String password) {
        int isAdmin = 0;
        
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Query the database to see if the name and password match an admin user
            ResultSet adminQuerry = stmt.executeQuery("SELECT COUNT(*) FROM utilizador WHERE utilizador.nome = '" + name + "' AND utilizador.passe = '" + password + "' AND utilizador.admin = true");

            // Get the result of the query
            while (adminQuerry.next()) {
                for (int i = 1; i < 7; i++) {
                    isAdmin = adminQuerry.getInt(i);
                }
            }

            // Close the result set, statement, and connection
            adminQuerry.close();
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
        return isAdmin;
    }
}
