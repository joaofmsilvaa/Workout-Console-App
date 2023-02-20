import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class delete
{

    static String ans;
    static Scanner input = new Scanner(System.in);

    public static void deleteMenu(Connection conn,int user_id,String name,String Originalpassword){
        boolean goBackListing = false;
        String[] databaseFields = new String[4];

        databaseFields[0] = "utilizador";
        databaseFields[1] = "exercicios";
        databaseFields[2] = "conjunto_exercicios";
        databaseFields[3] = "plano_treino";

        System.out.println("(1) Remove users         ");
        System.out.println("(2) Remove Exercises     ");
        System.out.println("(3) Remove Exercise sets ");
        System.out.println("(4) Remove Workout plans ");
        System.out.println("                         ");
        System.out.println("(E) Exit                 ");

        while(!goBackListing){
            System.out.println("Option:");
            Scanner optionScanner = new Scanner(System.in);
            String option = optionScanner.nextLine();

            int fieldNum = 0;

            if(option.equals("E") || option.equals("e")){
                goBackListing = true;
                System.out.print('\u000C');
                Log_in_admin.admin(conn,user_id,name,Originalpassword);
            }

            else if(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4")){
                fieldNum = Integer.parseInt(option); 

                if(fieldNum <= 4 && fieldNum > 0){
                    goBackListing = true;
                    System.out.print('\u000C');

                    main(conn,user_id,name,Originalpassword,databaseFields[fieldNum - 1]);
                }
                else{
                    System.out.println("The input does not correspond with any valid option");
                }

            }
            else{
                System.out.println("Invalid option");
            }
        }
    }

    public static void main(Connection conn,int user_id,String name,String Originalpassword, String table)
    {
        deleteData(conn,user_id,name,Originalpassword,table);
        System.out.println("");
        System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
        ans = input.nextLine();
        boolean cicle = true;

        if(ans.equals("r") || ans.equals("R")|| ans.equals("e") || ans.equals("E")){
            while(ans.equals("r") || ans.equals("R")){
                
                deleteData(conn,user_id,name,Originalpassword,table);
                System.out.println("");
                System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
                ans = input.nextLine();
            }
            while(ans.equals("e") || ans.equals("E")){
                ans = "";
                System.out.print('\u000C');
                Log_in_admin.admin(conn,user_id,name,Originalpassword);

            }
        }
    }

    public static int idObtainer(int givenId, String table, String column, String secondTable){

        int setId = 0;

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Get a statement from the connection
            Statement stmt = conn.createStatement() ;

            ResultSet userQuerry = stmt.executeQuery("SELECT "+column+" FROM "+table+" WHERE "+secondTable+" = "+ givenId + " LIMIT 1");

            stmt = conn.createStatement();
            while( userQuerry.next() ){

                for(int i = 1; i < 10;i++){
                    setId = userQuerry.getInt(1);
                }

            }

            // Close the result set, statement and the connection
            userQuerry.close() ;
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

        return setId;
    }

    public static void deleteData(Connection conn,int user_id,String name,String Originalpassword, String table)
    {
        try
        {
            String[] data = select.tablesInfo(table);

            System.out.print('\u000C');
            Statement stmt = conn.createStatement();
            select.selectData(conn,table);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            // Execute the query
            System.out.println("") ;
            System.out.println("Select a id to delete:") ;
            Scanner scanner = new Scanner(System.in);
            String id = scanner.nextLine();
            int id_num = Integer.parseInt(id);
            int result_set = 0;

            if(table.equals("utilizador")){
                result_set = deleteUser(id);
            }
            else if(table.equals("exercicios")){
                result_set = deleteExercise(id);
            }
            else if(table.equals("conjunto_exercicios")){
                result_set = deleteExSet(id);
            }
            else if(table.equals("plano_treino")){
                result_set = deleteWorkout(id);
            }
            if (result_set > 0)
            {
                System.out.println("Deleted successfully");
            }
            else
            {
                System.out.println("Can't delete data");
            }
            // Close the result set, statement and the connection
            stmt.close();
            conn.close();

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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

    public static int deleteWorkout(String setId){
        int result_set = 0;

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            PreparedStatement workoutPlan_delete = conn.prepareStatement("DELETE FROM plano_treino WHERE id_treino = ? ;");
            workoutPlan_delete.setString(1, setId);
            result_set = workoutPlan_delete.executeUpdate();
            System.out.println("Data deleted from plano_treino");

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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
        
        return result_set;
    }

    public static int deleteExSet(String setId){
        int result_set = 0;

        try{

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            PreparedStatement user_delete = conn.prepareStatement("DELETE FROM conjunto_exercicios WHERE id_conjunto = ? ;");
            user_delete.setString(1, setId);
            result_set = user_delete.executeUpdate();
            System.out.println("Data deleted from conjunto_exercicios");

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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
        
        return result_set;
    }

    public static int deleteExercise(String setId){
        int result_set = 0;

        try{

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            PreparedStatement  set_delete = conn.prepareStatement("DELETE FROM exercicios WHERE id_exercicio = ? ;");
            set_delete.setString(1, setId);
            result_set = set_delete.executeUpdate();
            System.out.println("Data deleted from exercicios");

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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
        
        return result_set;
    }

    public static int deleteUser(String setId){
        int result_set = 0;

        try{

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

            PreparedStatement user_delete = conn.prepareStatement("DELETE FROM utilizador WHERE id_user = ? ;");
            user_delete.setString(1, setId);
            result_set = user_delete.executeUpdate();
            System.out.println("Data deleted from utilizador");

        }
        catch(SQLException se)
        {
            System.out.println("SQL Exception:");

            // Loop through the SQL Exceptions
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
        
        return result_set;
    }
}