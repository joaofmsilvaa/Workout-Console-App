import java.util.Scanner;
import java.sql.*;

public class Log_in
{
    
    /**
     * Log-in menu main method - Asks if the user has an account on the app and
     * depending on the answer it redirects the user to another method
     */
    public static void logIN_Menu(Connection conn)
    {
        /*
         *  Initial menu
         */
        boolean goBack = false;

        while(!goBack)
        {
            System.out.println("If you wish to close the program insert \"E\"");
            System.out.println("Already have an account on the app?");
            System.out.println("Y/N:");
            Scanner optionSelector = new Scanner(System.in);
            String answer1 = optionSelector.nextLine();

            if(answer1.equals("Y") || answer1.equals("y"))
            {

                /*
                 * The variable goBack is true so the program dosen't ask the
                 * user for input again
                 */
                goBack = true;
                boolean goBackLogIN = false;
                LogIN();
            }

            else if(answer1.equals("N") || answer1.equals("n"))
            {

                goBack = true;
                boolean goBackLogIN = false;
                System.out.print('\u000C');
                registerUser.registerUsernameComplete();
            }

            else if(answer1.equals("E") || answer1.equals("e")){
                goBack = true;
                System.out.print('\u000C');
                System.out.print("closing program...");
                
            }
            
            /*
             * If the user enters a value diferent from Y or N the program tells
             * him that the value is invalid
             */

            else{
                // Limpa o chat
                System.out.print('\u000C');
                System.out.println("Invalid value");
            }
        }
    }

    /**
     * Log-in main method - Asks the user for a username and password and stores
     * them in a variable that is then returned so data can be used in other
     * methods
     **/
    public static void LogIN (){
        
        try
        {
            // Get a connection to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "" );

        System.out.println("Login");

        // Asks for a username
        System.out.println("Username:");
        Scanner usernameScanner = new Scanner(System.in);
        String name = usernameScanner.nextLine();

        // Asks for a password
        System.out.println("Password:");
        Scanner passwordScanner = new Scanner(System.in);
        String password = passwordScanner.nextLine();
        
        try{
            String ammountStr = searchUser.querry(name, password);
            int ammountInt = Integer.parseInt(ammountStr);
            
            String[] userinfos = searchUser.credentials(name, password);
            int user_id = Integer.parseInt(userinfos[0]);

            int adminResult = adminCheck.querry(name,password);
            
            account_Exists(conn,ammountInt,userinfos, adminResult, user_id);
        }
        
        catch(Exception error){
            String ammountStr = searchUser.querry(name, password);
            System.out.println("The data you inserted dosen't match any user in the database");
            LogIN();
        }
        
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }

    }

    public static void account_Exists (Connection conn,int result, String[] userinfos, int adminResult, int user_id){
        if(result == 1){
            if(adminResult == 1)
            {
                System.out.print('\u000C');
                       
                /*
                 * If the account details given by the user match an
                 * account with admin priveledges it logs in the admin
                 * account
                 */
                
                Log_in_admin.admin(conn,user_id,userinfos[1],userinfos[5]); 
            }

            else{
    
                Menus.menu1(conn,user_id,userinfos[1],userinfos[2],userinfos[3],userinfos[4],userinfos[5]);
            }
        }

        else
        {
            /*
             * If the account details given by the users don't match 
             * any account on the database the method LogIn_Error 
             * is called
             */
            LogIN_Error();
        }
    }
    
    /**
     * Log-in main method - If the password or username dont match a real account
     * a error message pops up
     **/
    public static void LogIN_Error(){
        Boolean goBackLogIN = true;
        Boolean goBack = false;

        // Clears the terminal
        System.out.print('\u000C');
        System.out.println("The password or username inserted are invalid.");
    }
}
