
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class remove
{

    static String ans;
    static Scanner input = new Scanner(System.in);

    public static void removeMenu(Connection conn,int user_id,String name,String Originalpassword){
        boolean goBackListing = false;
        String[] databaseFields = new String[2];

        databaseFields[0] = "conjunto_exercicios";
        databaseFields[1] = "plano_treino";

        System.out.println("(1) Remove from Exercise sets ");
        System.out.println("(2) Remove from Workout plans ");
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

            else if(option.equals("1") || option.equals("2")){
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
        removeData(conn,user_id,name,Originalpassword,table);
        System.out.println("");
        System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
        ans = input.nextLine();
        boolean cicle = true;

        if(ans.equals("r") || ans.equals("R")|| ans.equals("e") || ans.equals("E")){
            while(ans.equals("r") || ans.equals("R")){

                removeData(conn,user_id,name,Originalpassword,table);
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

    public static void removeData(Connection conn,int user_id,String name,String Originalpassword, String table)
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
            int result_set = 0;

            if(table.equals("conjunto_exercicios")){
                result_set = removeDataDB("conjunto_exercicios");
            }
            else if(table.equals("plano_treino")){
                result_set = removeDataDB("plano_treino");
            }

            if (result_set > 0)
            {
                System.out.println("Removed successfully");
            }
            else
            {
                System.out.println("Can't remove data");
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

    public static String[] columnsToInsert(Connection conn,String table){
        String[] columns = new String[4];

        if(table.equals("conjunto_exercicios")){
            columns = dataForSet(columns);
        }
        else{
            columns = dataForPlan(columns);
        }

        return columns;
    }

    public static String[] dataForSet(String[] columns){
        System.out.println("Insert the id of the exercise set that you want to remove a exercise:");
        Scanner planScanner = new Scanner(System.in);
        String setId = planScanner.next();

        System.out.println("Insert the id of the exercise that you want to remove from the set:");
        Scanner exerciseIdScanner = new Scanner(System.in);
        String exerciseId = exerciseIdScanner.next();

        columns[0] =  setId;
        columns[1] =  exerciseId;

        return columns;
    }

    public static String[] dataForPlan(String[] columns){
        System.out.println("Insert the id of the plan that you want to remove a exercise set:");
        Scanner planScanner = new Scanner(System.in);
        String planId = planScanner.next();

        System.out.println("Insert the id of the exercise set that you want to remove from the plan:");
        Scanner setIdScanner = new Scanner(System.in);
        String setId = setIdScanner.next();

        columns[0] =  planId;
        columns[1] =  setId;

        return columns;
    }

    public static int removeDataDB(String table){
        int result_set = 0;

        try{

            if(table.equals("conjunto_exercicios")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToInsert(conn,table);

                PreparedStatement exerciseSetOrPlan_insert = conn.prepareStatement("DELETE FROM "+table+" WHERE id_conjunto = ? AND id_exercicio = ?;");
                exerciseSetOrPlan_insert.setString(1, data[0]);
                exerciseSetOrPlan_insert.setString(2, data[1]);

                result_set = exerciseSetOrPlan_insert.executeUpdate();
            }
            
            else if(table.equals("plano_treino")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToInsert(conn,table);

                PreparedStatement exerciseSetOrPlan_insert = conn.prepareStatement("DELETE FROM "+table+" WHERE id_treino = ? AND id_conjunto = ?;");
                exerciseSetOrPlan_insert.setString(1, data[0]);
                exerciseSetOrPlan_insert.setString(2, data[1]);

                result_set = exerciseSetOrPlan_insert.executeUpdate();
            }
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
