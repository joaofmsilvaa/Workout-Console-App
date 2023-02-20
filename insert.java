
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class insert
{

    static String ans;
    static Scanner input = new Scanner(System.in);

    public static void insertMenu(Connection conn,int user_id,String name,String Originalpassword){
        boolean goBackListing = false;
        String[] databaseFields = new String[3];

        databaseFields[0] = "exercicios";
        databaseFields[1] = "conjunto_exercicios";
        databaseFields[2] = "plano_treino";

        System.out.println("(1) Create Exercises     ");
        System.out.println("(2) Create Exercise sets ");
        System.out.println("(3) Create Workout plans ");
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

            else if(option.equals("1") || option.equals("2") || option.equals("3")){
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
        insertData(conn,user_id,name,Originalpassword,table);
        System.out.println("");
        System.out.println("If you wish to reload enter \"R\" if you wish to exit click \"E\"");
        ans = input.nextLine();
        boolean cicle = true;

        if(ans.equals("r") || ans.equals("R")|| ans.equals("e") || ans.equals("E")){
            while(ans.equals("r") || ans.equals("R")){
                
                insertData(conn,user_id,name,Originalpassword,table);
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

    public static void insertData(Connection conn,int user_id,String name,String Originalpassword, String table)
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

            if(table.equals("exercicios")){
                result_set = insertData("exercicios");
            }
            else if(table.equals("conjunto_exercicios")){
                result_set = insertData("conjunto_exercicios");
            }
            else if(table.equals("plano_treino")){
                result_set = insertData("plano_treino");
            }

            if (result_set > 0)
            {
                System.out.println("Inserted successfully");
            }
            else
            {
                System.out.println("Can't insert data");
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
        String[] columns = new String[5];

        if(table.equals("exercicios")){
            String id = registerUser.idGetter(conn,"id_exercicio",table);

            columns = dataForExercise(columns,id);
        }
        else if(table.equals("conjunto_exercicios")){
            String id = registerUser.idGetter(conn,"id_conjunto",table);

            columns = dataForSet(columns,id);
        }
        else{
            String id = registerUser.idGetter(conn,"id_treino",table);

            columns = dataForPlan(columns,id);
        }

        return columns;
    }

    public static String[] dataForExercise(String[] columns, String id){
        System.out.println("Insert a name for your exercise:");
        Scanner nameScanner = new Scanner(System.in);
        String name = nameScanner.nextLine();

        System.out.println("Insert a zone for your exercise:");
        Scanner zoneScanner = new Scanner(System.in);
        String zone = zoneScanner.next();

        System.out.println("Choose the type of your exercise:");
        System.out.println("(1) Gain muscle");
        System.out.println("(2) Lose weight");
        Scanner typeScanner = new Scanner(System.in);
        String type = typeScanner.nextLine();

        if(type.equals("1") || type.equals("Gain muscle")){
            type = "massa";

        }
        else if(type.equals("2") || type.equals("Lose weight")){
            type = "peso";
        }

        System.out.println("Insert the difficulty of your exercise:");
        Scanner difScanner = new Scanner(System.in);
        String difficulty = difScanner.next();

        columns[0] =  id;
        columns[1] =  name;
        columns[2] = zone;
        columns[3] = type;
        columns[4] = difficulty;

        return columns;
    }

    public static String[] dataForSet(String[] columns, String id){
        System.out.println("Insert the id of the exercise that you want to insert in the set:");
        Scanner firstExScanner = new Scanner(System.in);
        String firstEx = firstExScanner.next();

        System.out.println("Insert \"null\" if the exercise dosen't have any repetitions");
        System.out.println("Insert the ammount of repetitions:");
        Scanner repsScanner = new Scanner(System.in);
        String repetitions = repsScanner.next();

        if(repetitions.equals("null")){
            repetitions = null;
        }

        System.out.println("Insert \"null\" if the exercise can't be measured by time");
        System.out.println("Insert the time of the exercise:");
        Scanner timeScanner = new Scanner(System.in);
        String time = timeScanner.next();

        if(time.equals("null")){
            time = null;
        }

        columns[0] =  id;
        columns[1] =  firstEx;
        columns[2] = repetitions;
        columns[3] = time;

        return columns;
    }

    public static String[] dataForPlan(String[] columns, String id){
        System.out.println("Insert the id of the exercise set that you want to insert in the plan:");
        Scanner firstSetScanner = new Scanner(System.in);
        String firstSet = firstSetScanner.next();

        System.out.println("Insert the day that the user is suppoused to do the exercise set:");
        Scanner dayScanner = new Scanner(System.in);
        String day = dayScanner.next();

        System.out.println("Choose the type of your exercise plan:");
        System.out.println("(1) Gain muscle");
        System.out.println("(2) Lose weight");
        Scanner typeScanner = new Scanner(System.in);
        String type = typeScanner.nextLine();

        if(type.equals("1")){
            type = "ganhar massa";

        }
        else{
            type = "Perder peso";
        }

        columns[0] =  id;
        columns[1] =  firstSet;
        columns[2] =  day;
        columns[3] =  type;

        return columns;
    }

    public static int insertData(String table){
        int result_set = 0;

        try{
            if(table.equals("exercicios")){
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToInsert(conn,table);

                PreparedStatement exercise_insert = conn.prepareStatement("INSERT INTO exercicios VALUES(?,?,?,?,?);");
                exercise_insert.setString(1, data[0]);
                exercise_insert.setString(2, data[1]);
                exercise_insert.setString(3, data[2]);
                exercise_insert.setString(4, data[3]);
                exercise_insert.setString(5, data[4]);

                result_set = exercise_insert.executeUpdate();
            }
            else{
                System.out.println();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/training_app", "root", "");

                String[] data = columnsToInsert(conn,table);

                PreparedStatement exerciseSetOrPlan_insert = conn.prepareStatement("INSERT INTO "+table+" VALUES(?,?,?,?);");
                exerciseSetOrPlan_insert.setString(1, data[0]);
                exerciseSetOrPlan_insert.setString(2, data[1]);
                exerciseSetOrPlan_insert.setString(3, data[2]);
                exerciseSetOrPlan_insert.setString(4, data[3]);

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
