import java.sql.*;

/**
 * Checks if the given credentials match an existing account then stores the credentials if they match an account
 * 
 * @author  João Silva
 * @version 02/02/2023
 */
public class searchUser
{
    /**
     * Querry main method - Counts the ammount of users in the database that have the name and passwords given before.
     * 
     * @param a name and password
     * @return the ammount of accounts with that name and password
     **/
    public static String querry(String name, String password){
        String userAmmount = null;

        try{

            // Get a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

            // Get a statement from the connection
            Statement stmt = conn.createStatement();

            // Queries the database to count the number of accounts with the given name and password
            ResultSet userQuerry = stmt.executeQuery("SELECT COUNT(*) FROM utilizador WHERE utilizador.nome = '"+name+"' AND utilizador.passe = '"+password+"'");

            // Processes the result set
            while(userQuerry.next()){
                userAmmount = userQuerry.getString(1);
            }

            // Closes the result set, statement and connection
            userQuerry.close();
            stmt.close();
            conn.close();

        }

        // Handles SQL exceptions
        catch( SQLException se ){
            System.out.println("SQL Exception:");
            while( se != null ){
                System.out.println("State  : " + se.getSQLState());
                System.out.println("Message: " + se.getMessage());
                System.out.println("Error  : " + se.getErrorCode());
                se = se.getNextException();
            }
        }

        return userAmmount;
    }

    public static String[] credentials(String name, String password){
        String[] infos = new String[7];

        try{
            // Get a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

            // Get a statement from the connection
            Statement stmt = conn.createStatement() ;

            // Execute the query
            ResultSet rs = stmt.executeQuery("SELECT id_user,nome,objetivo,peso,altura,passe,admin FROM utilizador WHERE utilizador.nome = '"+name+"' AND utilizador.passe = '"+password+"'" );

            // Loop through the result set
            while( rs.next() ){
                for(int i = 1; i < 8;i++){
                    infos[i - 1] = rs.getString(i);

                }
            }

            // Close the result set, statement and the connection
            rs.close() ;
            stmt.close() ;
            conn.close() ;
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

        return infos;
    }
}
